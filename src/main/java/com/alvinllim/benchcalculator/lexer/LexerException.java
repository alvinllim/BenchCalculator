package com.alvinllim.benchcalculator.lexer;

/**
 * @author Alvin
 *
 */
@SuppressWarnings("serial")
public class LexerException extends Exception {

	private char errorCharacter;
	private int errorLocation;

	public LexerException(char errorCharacter, int errorLocation) {
		super("Illegal character: "+errorCharacter+" at column "+errorLocation);
		this.errorCharacter = errorCharacter;
		this.errorLocation = errorLocation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LexerException [errorCharacter=" + errorCharacter
				+ ", errorLocation=" + errorLocation + "]";
	}

}
