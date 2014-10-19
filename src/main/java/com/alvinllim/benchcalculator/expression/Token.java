package com.alvinllim.benchcalculator.expression;

/**
 * @author alvinllim
 *
 */
public class Token {

	private String value;
	private TokenType type;
	private int columnIndex;

	public Token(String value, TokenType type, int colIndex) {
		this.value = value;
		this.type = type;
		this.columnIndex = colIndex;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Token [value=" + value + ", type=" + type + ", columnIndex=" + columnIndex + "]";
	}

}
