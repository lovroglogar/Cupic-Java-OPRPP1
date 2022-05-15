package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Predstavlja For Loop Node tag
 * @author Lovro Glogar
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Prvi argument je varijabla
	 */
	private ElementVariable variable;
	
	/**
	 * Drugi argument
	 */
	private Element startExpression;
	
	/**
	 * Treci argument
	 */
	private Element endExpression;
	
	/**
	 * Cetvrti element
	 */
	private Element stepExpression;

	/**
	 * Konstruktor
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Konstruktor
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
	}

	/**
	 * Vraca <b>variable</b>
	 * @return <b>variable</b>
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Vraca <b>startExpression</b>
	 * @return <b>startExpression</b>
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Vraca <b>endExpression</b>
	 * @return <b>endExpression</b>
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Vraca <b>stepExpression</b>
	 * @return <b>stepExpression</b>
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Vraca string reprezentaciju for loop taga
	 */
	@Override
	public String toString() {
		if(stepExpression == null) {
			return new String(variable.asText() + " " + startExpression.asText() 
								+ " " + endExpression.asText());
		}else 
			return new String(variable.asText() + " " + startExpression.asText() 
								+ " " + endExpression.asText() + " " + stepExpression.asText());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}
	
}
