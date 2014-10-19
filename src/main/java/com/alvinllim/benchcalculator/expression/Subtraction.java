package com.alvinllim.benchcalculator.expression;

/**
 * @author alvinllim
 *
 */
public class Subtraction implements Expression {
	
	private Expression lhs;
	private Expression rhs;
	
	public Subtraction(Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.expression.Expression#evaluate()
	 */
	@Override
	public Double evaluate() {
		return lhs.evaluate() - rhs.evaluate();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subtraction [lhs=" + lhs + ", rhs=" + rhs + "]";
	}

}
