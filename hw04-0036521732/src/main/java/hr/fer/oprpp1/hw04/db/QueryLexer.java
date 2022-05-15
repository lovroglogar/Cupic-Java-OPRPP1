package hr.fer.oprpp1.hw04.db;

public class QueryLexer {
	
	private char[] line;
	
	/**
	 * Zadnji odredeni token
	 */
	private QueryToken token;
	
	/**
	 * Indeks prvog neobradenog znaka
	 */
	private int currentIndex;

	public QueryLexer(String line) {
		if(line == null)
			throw new NullPointerException();
		this.line = line.toCharArray();
		this.currentIndex = 0;
	}
	
	public QueryToken nextToken() {
		preskociBjeline();
		if(token != null && token.getType().equals(QueryTokenType.EOF))
			throw new ParserException();
		if(this.currentIndex == line.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			return token;
		}

		StringBuilder sb;
		if(Character.isLetter(line[currentIndex])) {
			sb = new StringBuilder();
			sb.append(line[currentIndex++]);
			
			while(Character.isLetter(line[currentIndex])) {
				sb.append(line[currentIndex]);
				currentIndex++;
				if(currentIndex == line.length)
					break;
			}
			
			String s = sb.toString();
			QueryTokenType type;
			if(sb.toString().equals("LIKE"))
				type = QueryTokenType.OPERATOR;
			else if (s.toLowerCase().equals("and"))
				type = QueryTokenType.AND;
			else type = QueryTokenType.ATTRIBUTE;
			
			token = new QueryToken(type, s);
			return token;
		} else if(line[currentIndex] == '\"') {
			currentIndex++;
			sb = new StringBuilder();
			while(line[currentIndex] != '\"') {
				sb.append(line[currentIndex++]);
			}
			
			token = new QueryToken(QueryTokenType.LITERAL, sb.toString());
			currentIndex++;
			return token;
		} else {
			sb = new StringBuilder();
			sb.append("");
		
			while(!Character.isLetter(line[currentIndex]) && !Character.isWhitespace(line[currentIndex]) && line[currentIndex] != '\"') 
				sb.append(line[currentIndex++]);
			if(sb.isEmpty())
				throw new ParserException("Operator error");
			token = new QueryToken(QueryTokenType.OPERATOR, sb.toString());
			return token;
		}
	}
	
	public QueryToken getToken() {
		return this.token;
	}
	
	private void preskociBjeline() {
		char currentCharacter;
		while(currentIndex < this.line.length) {
			currentCharacter = this.line[currentIndex];
			if( currentCharacter == ' ' 
				|| currentCharacter == '\t') {
				currentIndex++;
			} else 
				return;
		}
	}
}
