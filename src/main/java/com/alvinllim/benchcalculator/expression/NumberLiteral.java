package com.alvinllim.benchcalculator.expression;

/**
 * @author Alvin
 *
 */
public class NumberLiteral implements Expression {
	
	private double value;

	public NumberLiteral(String input) {
		this.value = Double.parseDouble(input);
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.expression.Expression#evaluate()
	 */
	@Override
	public Double evaluate() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NumberLiteral [value=" + value + "]";
	}

}
