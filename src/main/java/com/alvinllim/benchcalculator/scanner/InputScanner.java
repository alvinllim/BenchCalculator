package com.alvinllim.benchcalculator.scanner;

/**
 * Reads the input one character at a time, and allows lookup of subsequent characters.
 * 
 * @author Alvin
 *
 */
public interface InputScanner {

	public char getNextCharacter();
	
	public boolean hasMoreCharacters();
	
	/**
	 * @param offset the number of characters to look ahead
	 * @return the character at the specified offset from the current location, or null character if the input length is exceeded by the offset 
	 */
	public char lookahead(int offset);
	
	/**
	 * @return index of current character being accessed, starting with 1 for the first character.
	 */
	public int getCurrentIndex();
	
}
