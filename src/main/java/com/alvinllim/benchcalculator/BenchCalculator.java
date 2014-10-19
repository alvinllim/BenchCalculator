package com.alvinllim.benchcalculator;

import com.alvinllim.benchcalculator.parser.ParserException;

/**
 * @author alvinllim
 *
 */
public interface BenchCalculator {

	public Double evaluate(String input) throws ParserException;
}
