package com.alvinllim.benchcalculator.lexer;

import com.alvinllim.benchcalculator.expression.Token;

/**
 * @author alvinllim
 *
 */
public interface Lexer {

	public Token nextToken() throws LexerException;
	
}
