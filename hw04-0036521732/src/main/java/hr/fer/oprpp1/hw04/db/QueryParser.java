package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
	
	private QueryLexer lexer;
	
	private String line;
	
	private boolean isDirectQuery;
	
	private List<ConditionalExpression> queryExpressions;
	
	public QueryParser(String line) {
		this.line = line;
		this.lexer = new QueryLexer(this.line);
		this.queryExpressions = new ArrayList<>();
		this.parse();
	}
	
	private void parse() {
		
		
		QueryToken token = lexer.nextToken();
		boolean expectedAndToken = false;
		
		if(token.getType() == QueryTokenType.EOF) {
			throw new ParserException("Empty query not allowed");
		}
		
		while(token.getType() != QueryTokenType.EOF) {
			if(token.getType() != QueryTokenType.AND && !expectedAndToken) {
				//Ocekujemo barem 3 tokena za redom koji nisu EOF
				IFieldValueGetter fieldValueGetter;
				IComparisonOperator comparisonOperator;
				String literal = null;
				
				if(token.getType() == QueryTokenType.ATTRIBUTE) {
					if(token.getValue().equals("firstName"))
						fieldValueGetter = FieldValueGetters.FIRST_NAME;
					else if (token.getValue().equals("lastName"))
						fieldValueGetter = FieldValueGetters.LAST_NAME;
					else if (token.getValue().equals("jmbag"))
						fieldValueGetter = FieldValueGetters.JMBAG;
					else throw new ParserException("Unrecognized attribute");
					token = lexer.nextToken();
				} else throw new ParserException("Attribute expected but was " + token.getType());
				
				if(token.getType() == QueryTokenType.OPERATOR) {
					if(token.getValue().equals("<"))
						comparisonOperator = ComparisonOperators.LESS;
					else if(token.getValue().equals("<="))
						comparisonOperator = ComparisonOperators.LESS_OR_EQULS;
					else if(token.getValue().equals(">"))
						comparisonOperator = ComparisonOperators.GREATER;
					else if(token.getValue().equals(">="))
						comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
					else if(token.getValue().equals("="))
						comparisonOperator = ComparisonOperators.EQUALS;
					else if(token.getValue().equals("!="))
						comparisonOperator = ComparisonOperators.NOT_EQUALS;
					else if(token.getValue().equals("LIKE"))
						comparisonOperator = ComparisonOperators.LIKE;
					else throw new ParserException("Unrecognized operator");
					token = lexer.nextToken();
				} else throw new ParserException("Operator error");
				
				if(token.getType() == QueryTokenType.LITERAL) {
					literal = (String) token.getValue();
					token = lexer.nextToken();
				} else throw new ParserException("String literal error");
				
				expectedAndToken = true;
				queryExpressions.add(new ConditionalExpression(fieldValueGetter, literal, comparisonOperator));
			} else {
				if(!expectedAndToken)
					throw new ParserException("End token error");
				expectedAndToken = false;
				token = lexer.nextToken();
				if(token.getType() == QueryTokenType.EOF)
					throw new ParserException("query must not end with logical operator 'AND'");
			}
		}
		
		if(queryExpressions.size() == 1 && queryExpressions.get(0).getFieldValueGetter().equals(FieldValueGetters.JMBAG)
				&& queryExpressions.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS))
			this.isDirectQuery = true;
		else this.isDirectQuery = false;
	}
	
	public boolean isDirectQuery() {
		return this.isDirectQuery;
	}
	
	public String getQueriedJMBAG() {
		if(!this.isDirectQuery)
			throw new IllegalStateException();
		
		return queryExpressions.get(0).getLiteral();
	}
	
	public List<ConditionalExpression> getQuery(){
		return this.queryExpressions;
	}

}
