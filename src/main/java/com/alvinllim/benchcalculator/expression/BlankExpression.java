package com.alvinllim.benchcalculator.expression;

/**
 * @author Alvin
 *
 */
public class BlankExpression implements Expression {

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.expression.Expression#evaluate()
	 */
	@Override
	public Double evaluate() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlankExpression []";
	}

}
