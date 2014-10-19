package com.alvinllim.benchcalculator.scanner;

import com.google.common.base.Preconditions;

/**
 * Scanner implementation for processing a single {@code String}.
 * 
 * @author alvinllim
 *
 */
public class InputStringScanner implements InputScanner {
	
	private final String input;
	private final int inputLength;
	private int currentIndex = -1;

	public InputStringScanner(String inputString) {
		Preconditions.checkNotNull(inputString);
		this.input = inputString;
		this.inputLength = input.length();
	}
	
	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.InputScanner#getNextCharacter()
	 */
	@Override
	public char getNextCharacter() {
		return input.charAt(++currentIndex);
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.InputScanner#hasMoreCharacters()
	 */
	@Override
	public boolean hasMoreCharacters() {
		return currentIndex + 1 < inputLength;
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.InputScanner#lookahead(int)
	 */
	@Override
	public char lookahead(int offset) {
		int lookaheadIndex = currentIndex + offset;
		if (lookaheadIndex >= inputLength) {
			return 0;
		} else {
			return input.charAt(lookaheadIndex);
		}
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.parser.InputScanner#getCurrentIndex()
	 */
	@Override
	public int getCurrentIndex() {
		return currentIndex + 1;
	}
	
	
}
