package com.alvinllim.benchcalculator.parser;

import com.alvinllim.benchcalculator.expression.Expression;

/**
 * @author alvinllim
 *
 */
public interface Parser {

	public Expression parse() throws ParserException;
}
