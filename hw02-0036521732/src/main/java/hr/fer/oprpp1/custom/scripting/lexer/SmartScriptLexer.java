package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * Lexer koji provodi leksicku analizu teksta;
 * analizira i vraca jednu po jednu leksicku jedinku
 * @author Lovro Glogar
 *
 */
public class SmartScriptLexer {
	
	/**
	 * Ulazni tekst u Lexer
	 */
	private char[] data;
	
	/**
	 * Zadnji odredeni token
	 */
	private SmartScriptToken token;
	
	/**
	 * Indeks prvog neobradenog znaka
	 */
	private int currentIndex;
	
	/**
	 * Stanje lexera
	 */
	private SmartScriptLexerState state;

	/**
	 * Konstruktor
	 * @param text tekst koji se parsira
	 * @throws NullPointerException u slucaju da je <b>text</b> null referenca
	 */
	public SmartScriptLexer(String text) { 
		if(text == null)
			throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.OUTSIDE_OF_TAGS;
	}
	
	/**
	 * Generira i vraća sljedeći token
	 * @return sljedeci token
	 * @throws LexerException u slucaju greske
	 */
	public SmartScriptToken nextToken() {
		if(token != null && token.getType().equals(SmartScriptTokenType.EOF)) // provjera je li nextToken() pozvana ako je tekst vec cijeli analiziran
			throw new SmartScriptParserException();
		if(this.currentIndex >= data.length) { // provjera ako smo dosli do kraja teksta da se vrati EOF token
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}
		
		char currentCharacter = this.data[currentIndex]; // prvi neanalizirani znak teksta
		
		StringBuilder sb;
		
		// provjera ako se lexer nalazi na pocetku taga da postavi stanje u INSIDE_OF_TAGS
		// i preskoci znakove '{' i '$'
		if(tagStartsNow(currentCharacter)) { 
			this.setState(SmartScriptLexerState.INSIDE_OF_TAGS);
			currentIndex += 2;
		}
		
		//Nacin analiziranja se odreduje na temelju stanja u kojem se lexer nalazi
		if(this.state == SmartScriptLexerState.OUTSIDE_OF_TAGS) { // analiza teksta izvan taga
			sb = new StringBuilder();
			sb.append("");
			
			if(!tagStartsNow(currentCharacter)) { // ako se ne nalazimo na pocetku taga
				currentCharacter = processEscapeCharacterOutsideTags(currentCharacter, sb); // procesira ako je escape znak sljedeci
				sb.append(currentCharacter);
				if(!reachedEOF()) {
					currentCharacter = this.data[++currentIndex];
					while(!tagStartsNow(currentCharacter)) { // prolazi kroz znakove dok se ne dode do pocetka taga
						currentCharacter = processEscapeCharacterOutsideTags(currentCharacter, sb);
						sb.append(currentCharacter);
						if(!reachedEOF()) 
							currentCharacter = this.data[++currentIndex];
						else { // prekini petlju ako se nalazimo na kraju teksta
							currentIndex++;
							break;
						}
					}
				}
			}
			
			currentIndex += 2;	// PRESKACEM { I @ JER SE NALAZIM NA POCETKU TAG-A
			setState(SmartScriptLexerState.INSIDE_OF_TAGS);
			if(!sb.toString().equals("")) // vrati novi text_node token ako nije prazan string
				token = new SmartScriptToken(SmartScriptTokenType.TEXT_NODE, new TextNode(sb.toString()));
			return token;
		} else { // NALAZIMO SE UNUTAR TAGA; stanje INSIDE_TAG
			preskociBjeline(); // preskace bjeline
			
			//Odredi jesmo li u for_loop tagu ili u echo tagu
			boolean inForLoop = String.valueOf(data, currentIndex, 3).toUpperCase().equals("FOR");
			boolean inEcho = data[currentIndex] == '=';
			
			ArrayIndexedCollection elements = new ArrayIndexedCollection(4); // kolekcija u koju spremamo elemente taga
			
			if(inForLoop || inEcho) {
				if(inForLoop) // ako sam u for loop tagu, preskoci znakove 'f', 'o' i 'r'
					currentIndex += 3;
				else currentIndex++; // inace smo u echo tagu i preskacemo '='
				
				preskociBjeline();
				currentCharacter = data[currentIndex];
				String varijabla;
				
				while(!tagEndsNow(currentCharacter)) { // obradujemo elemente taga do kraja taga; pojave znakova '$' i '}'
					//Obrada ako je varijabla
					varijabla = obradiAkoJeVarijabla(currentCharacter);
					if(varijabla != null) {
						elements.add((ElementVariable) new ElementVariable(varijabla));
					}
					
					preskociBjeline();
					currentCharacter = data[currentIndex];
					currentCharacter = processEscapeCharacterInsideTags(currentCharacter, null);
					// Obradi double ili integer
					if(Character.isDigit(currentCharacter) || negativeNumberComesNext()) {
						sb = new StringBuilder();
						if(negativeNumberComesNext()) { // provjera ako slijedi negativni znak
							sb.append(currentCharacter);
							currentCharacter = data[++currentIndex];
						}
						
						sb.append(currentCharacter);
						currentCharacter = data[++currentIndex];
						boolean isDecimal = false; // prati jesmo li vec procitali decimalnu tocku
						
						while(Character.isDigit(currentCharacter) || currentCharacter == '.') { // citaj broj i pazi ako je decimalan mozda
							if(currentCharacter == '.' && isDecimal) 
								throw new SmartScriptParserException();
							else if(currentCharacter == '.' && !reachedEOF()) {
								if(Character.isDigit(data[currentIndex + 1])) {
									isDecimal = true;
									sb.append(currentCharacter);
									sb.append(data[++currentIndex]);
									currentCharacter = data[++currentIndex];
									continue;
								}
							}
							
							sb.append(currentCharacter);
							currentCharacter = data[++currentIndex];
						}
						
						if(isDecimal) { // ako je decimalni broj dodajem ga u elemente taga kao Double
							elements.add(new ElementConstantDouble(Double.parseDouble(sb.toString())));
						} else // inace kao Integers
							elements.add(new ElementConstantInteger(Integer.parseInt(sb.toString())));
					}
					
					preskociBjeline();
					currentCharacter = data[currentIndex];
					
					//Obrada ako je String
					if(currentCharacter == '"') { // pocetak stringa
						sb = new StringBuilder();
						currentCharacter = data[++currentIndex];
						processEscapeCharacterInsideTags(currentCharacter, sb); 
						while(currentCharacter != '"') {
							currentCharacter = processEscapeCharacterInsideTags(currentCharacter, sb);
							if(currentCharacter != '"') {
								sb.append(currentCharacter);
								currentCharacter = data[++currentIndex];
							}
						}
						elements.add(new ElementString(sb.toString()));
						currentIndex++;
					}
					
					preskociBjeline();
					currentCharacter = data[currentIndex];
					
					//Obrada ako je funkcija
					if(currentCharacter == '@') {
						currentCharacter = data[++currentIndex];
						varijabla = obradiAkoJeVarijabla(currentCharacter); // ime funkcije obradujemo kao varijablu
						if(varijabla != null)
							elements.add(new ElementFunction(varijabla));
						else
							throw new SmartScriptParserException();
					}
					
					preskociBjeline();
					currentCharacter = data[currentIndex];
					
					//Obrada ako je operator
					if( currentCharacter == '+' ||
						(currentCharacter == '-' && !negativeNumberComesNext()) ||
						currentCharacter == '*' ||
						currentCharacter == '/' ||
						currentCharacter == '^') {
						elements.add(new ElementOperator(String.valueOf(currentCharacter)));
						currentCharacter = data[++currentIndex];
					}
				}
				
				currentIndex += 2; // Preskcacem $ i } jer se nalazim na kraju TAG-a
				this.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS);
				
				if(inForLoop) {
					try {
						ForLoopNode  node = null;
						if(elements.size() == 4) {
							node = new ForLoopNode( (ElementVariable) elements.get(0), 
															(Element) elements.get(1),
															(Element) elements.get(2),
															(Element) elements.get(3));
						}else if(elements.size() == 3) {
							node = new ForLoopNode( (ElementVariable) elements.get(0), 
									(Element) elements.get(1),
									(Element) elements.get(2));
						}else
							throw new SmartScriptParserException();
						
						token = new SmartScriptToken(SmartScriptTokenType.FOR_LOOP_NODE, node);
						return token;
					} catch (Exception e) {
						throw e;
					}
				}
				
				if(inEcho) {
					try { // premjesatm elmente taga u Element[] i stvaram echo token
						Object[] temp = elements.toArray();
						Element[] array = new Element[temp.length];
						for(int i = 0; i < temp.length; i++)
							array[i] = (Element) temp[i];
						EchoNode node = new EchoNode(array);
						token = new SmartScriptToken(SmartScriptTokenType.ECHO_NODE, node);
						return token;
					} catch (Exception e) {
						throw e;
					}
				}
				
				throw new SmartScriptParserException(); // ako smo dosli do ovdje nesto ne stima
			} else if( String.valueOf(data, currentIndex, 3).toUpperCase().equals("END")) {
				//Obrada kraja ForLoop taga
				currentIndex += 3; // preskacem END
				preskociBjeline();
				currentIndex += 2; // preskacem $}
				setState(SmartScriptLexerState.OUTSIDE_OF_TAGS);
				return new SmartScriptToken(SmartScriptTokenType.FOR_LOOP_END, null);
			} else 
				throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Vraca zadnji generirani token; moze se pozivati vise puta; ne pokrece generiranje sljedeceg tokena
	 * @return zadnji generirni token
	 */
	public SmartScriptToken getToken() {
		return token;
	}
	
	/**
	 * Postavlja stanje lexera na <code>state</code>
	 * @param state novo stanje lexera
	 */
	public void setState(SmartScriptLexerState state) {
		this.state = state;
	}
	
	/**
	 * preskace ' ', '\n', '\t' i '\r' u izvornom teksu
	 */
	private void preskociBjeline() {
		char currentCharacter;
		while(currentIndex < this.data.length) {
			currentCharacter = this.data[currentIndex];
			if( currentCharacter == ' ' 
				|| currentCharacter == '\n' 
				|| currentCharacter == '\t'
				|| currentCharacter == '\r') {
				currentIndex++;
			} else 
				return;
		}
	}
	
	/**
	 * Vraca true ako je <b>c</b> escape znak
	 * @param c
	 * @return
	 */
	private boolean isEscapeCharacter(char c) {
		return c == '\\';
	}
	
	/**
	 * Ako se u tagu nalazi niz znakova "\"", tada se znak " ne gleda kao pocetak ili kraj string elementa taga
	 * @param currentCharacter trenutni znak
	 * @param sb
	 * @return novi trenutni znak
	 * @throws SmartScriptParserException u slucaju ilegalnog escape znaka
	 */
	private char processEscapeCharacterInsideTags(char currentCharacter, StringBuilder sb) {
		if(isEscapeCharacter(currentCharacter) && !reachedEOF()) {
			if(data[currentIndex + 1] == '"') {
				sb.append("\\\"");
				currentIndex += 2;
				currentCharacter = data[currentIndex];
			} else if ( data[currentIndex + 1] != ' ' && data[currentIndex + 1] != '\n' && data[currentIndex + 1] != '\r' 
						&& data[currentIndex + 1] != '\t')
				throw new SmartScriptParserException();
		}
		return currentCharacter;
	}
	
	/**
	 * Ako se van taga nalazi znak '\' samostalno ili ispred znaka'{' to je valjano koristenje escape znaka
	 * @param currentCharacter
	 * @param sb
	 * @return
	 */
	private char processEscapeCharacterOutsideTags(char currentCharacter, StringBuilder sb) {
		if(isEscapeCharacter(currentCharacter) && !reachedEOF()) {
			if( data[currentIndex + 1] == '{') {
				currentCharacter = data[++currentIndex];
				sb.append("\\");
			} else if ( data[currentIndex + 1] != ' ' && data[currentIndex + 1] != '\n' && data[currentIndex + 1] != '\r' 
						&& data[currentIndex + 1] != '\t')
				throw new SmartScriptParserException();
		}
		return currentCharacter;
	}

	/**
	 * Vraca True ako se nalaizmo na kraju datoteke
	 * @return
	 */
	private boolean reachedEOF() {
		return (currentIndex + 1) == data.length;
	}
	
	/**
	 * Vraca True ako je trenutni znak jednak '{' i sljedeci jednak '$'
	 * @param currentCharacter
	 * @return
	 */
	private boolean tagStartsNow(char currentCharacter) {
		if(currentCharacter == '{' && !reachedEOF()) {
			if(this.data[currentIndex + 1] == '$')
				return true;
		}
		return false;
	}
	
	/**
	 * Vraca True ako je trenutni znak jednak '$' i sljedeci jednak '}'
	 * @param currentCharacter
	 * @return
	 */
	private boolean tagEndsNow(char currentCharacter) {
		if(currentCharacter == '$' && !reachedEOF()) {
			if(this.data[currentIndex + 1] == '}')
				return true;
		}
		return false;
	}
	
	/**
	 * Vraca true ako je sljedeci znak jednak '-' a direktno iza njega slijedi znamenka
	 * @return
	 */
	private boolean negativeNumberComesNext() {
		if(data[currentIndex] == '-' && !reachedEOF()) {
			if(Character.isDigit(data[currentIndex + 1]))
				return true;
		}
		return false;
	}
	
	/**
	 * Obrada niza ako je varijabla
	 * @param currentCharacter
	 * @return
	 */
	private String obradiAkoJeVarijabla(Character currentCharacter) {
		if(Character.isLetter(currentCharacter)) { // ako pocinje slovom
			StringBuilder sb = new StringBuilder();
			sb.append(currentCharacter);
			currentCharacter = data[++currentIndex];
			// iza prvog slova moze ali i ne mora slijediti niz slova, znamenki ili znaka '_'
			while(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_') {
				sb.append(currentCharacter);
				currentCharacter = data[++currentIndex];	
			}
			return sb.toString();
		}
		return null;
	}

	
}
