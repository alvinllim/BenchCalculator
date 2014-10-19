package com.alvinllim.benchcalculator.parser;

import com.alvinllim.benchcalculator.expression.Token;
import com.alvinllim.benchcalculator.lexer.LexerException;

/**
 * @author Alvin
 *
 */
@SuppressWarnings("serial")
public class ParserException extends Exception {

	public ParserException(LexerException e) {
		super(e);
	}

	public ParserException(Token currentToken) {
		super("Illegal token found "+currentToken);
	}

}
