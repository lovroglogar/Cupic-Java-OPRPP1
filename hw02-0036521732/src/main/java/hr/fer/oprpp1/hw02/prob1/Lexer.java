package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa parsira dani niz
 * @author Lovro Glogar
 */
public class Lexer {
	
	/**
	 * Ulazni tekst u Lexer
	 */
	private char[] data;
	
	/**
	 * Trenutni token
	 */
	private Token token;
	
	/**
	 * Indeks prvog neobradenog znaka
	 */
	private int currentIndex;
	
	/**
	 * Stanje lexera
	 */
	private LexerState state;
	
	/**
	 * Konstruktor
	 * @param text tekst koji se parsira
	 * @throws NullPointerException u slucaju da je <b>text</b> null referenca
	 */
	public Lexer(String text) { 
		if(text == null)
			throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Generira i vraća sljedeći token
	 * @return sljedeci token
	 * @throws LexerException u slucaju greske
	 */
	public Token nextToken() {
		preskociBjeline();
		if(token != null && token.getType().equals(TokenType.EOF))
			throw new LexerException();
		if(this.currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		char currentCharacter = this.data[currentIndex];
		
		StringBuilder sb = new StringBuilder();
		
		//Provjeri jeli se pojavio znak #
		if(currentCharacter == '#') {
			if(this.state == LexerState.BASIC) {
				//Extended nacin rada
				setState(LexerState.EXTENDED);
			} else {
				//Povratak u basic nacin rada
				setState(LexerState.BASIC);
			}
			currentIndex++;
			token = new Token(TokenType.SYMBOL, currentCharacter);
			return token;
		}
		
		
		
		if(this.state == LexerState.BASIC) {
			// pocinje provjera za rijeci
			if( Character.isLetter(currentCharacter) || isEscapeCharacter(currentCharacter)) {
				currentCharacter = processEscapeCharacter(currentCharacter);
				sb.append(currentCharacter);
				if(!reachedEOF()) {
					currentCharacter = this.data[++currentIndex];
					while(Character.isLetter(currentCharacter) || isEscapeCharacter(currentCharacter)) {
						currentCharacter = processEscapeCharacter(currentCharacter);
						sb.append(currentCharacter);
						if(!reachedEOF())
							currentCharacter = this.data[++currentIndex];
						else { 
							currentIndex++;
							break;
						}
					}
				}
				token = new Token(TokenType.WORD, sb.toString());
				return token;
			}
			
			//pocinje provjera za brojeve
			if(Character.isDigit(currentCharacter)) {
				sb.append(currentCharacter);
				if(!reachedEOF()) {
					currentCharacter = this.data[++currentIndex];
					while(Character.isDigit(currentCharacter)) {
						sb.append(currentCharacter);
						if(!reachedEOF())
							currentCharacter = this.data[++currentIndex];
						else { 
							currentIndex++;
							break;
						}
					}
				}
				try {
					token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
					return token;
				} catch(NumberFormatException e) {
					throw new LexerException();
				}
			}
			
			//pocinje provjera za simbol
			if(!Character.isLetter(currentCharacter) && !Character.isDigit(currentCharacter) && !isEscapeCharacter(currentCharacter)) {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, currentCharacter);
				return token;
			}
			
		} else { // rad u EXTENDED stanju
			sb.append(currentCharacter);
			if(!reachedEOF()) {
				currentCharacter = this.data[++currentIndex];
				while(currentCharacter != '#' && currentCharacter != ' ') {
					sb.append(currentCharacter);
					if(!reachedEOF())
						currentCharacter = this.data[++currentIndex];
					else { 
						currentIndex++;
						break;
					}
				}
				
				token = new Token(TokenType.WORD, sb.toString());
				return token;
			}
		}
		
		throw new LexerException();
	}
	
	/**
	 * Vraca zadnji generirani token; moze se pozivati vise puta; ne pokrece generiranje sljedeceg tokena
	 * @return zadnji generirni token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Postavlja stanje lexera na <code>state</code>
	 * @param state novo stanje lexera
	 */
	public void setState(LexerState state) {
		if(state == null)
			throw new NullPointerException();
		this.state = state;
	}
	
	
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
	
	private boolean isEscapeCharacter(char c) {
		return c == '\\';
	}
	
	/**
	 * Provjerava greske u sintaksi vezane uz escape znak
	 * @param currentCharacter trenutni znak parsera
	 * @return trenutni znak parsera
	 * @throws LexerException u slucaju da je escape znak na neposredno prije kraja dokumenta
	 * ili znak neposredno iza escape znaka nije znamenka ili sam znak \
	 */
	private char processEscapeCharacter(char currentCharacter) {
		if(isEscapeCharacter(currentCharacter)) {
			if(currentIndex == data.length - 1)
				throw new LexerException();
			currentCharacter = this.data[++currentIndex];
			if(!Character.isDigit(currentCharacter) && currentCharacter != '\\')
				throw new LexerException();
		}
		return currentCharacter;
	}
	
	private boolean reachedEOF() {
		return (currentIndex + 1) == data.length;
	}
}

