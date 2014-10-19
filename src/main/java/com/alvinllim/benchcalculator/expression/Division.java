package com.alvinllim.benchcalculator.expression;

/**
 * @author alvinllim
 *
 */
public class Division implements Expression {
	
	private Expression numerator;
	private Expression denominator;
	
	public Division(Expression numerator, Expression denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.expression.Expression#evaluate()
	 */
	@Override
	public Double evaluate() {
		return Double.valueOf((int) (numerator.evaluate() / denominator.evaluate()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Division [numerator=" + numerator + ", denominator=" + denominator + "]";
	}

}
