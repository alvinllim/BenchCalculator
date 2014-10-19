package com.alvinllim.benchcalculator;

import com.alvinllim.benchcalculator.expression.Expression;
import com.alvinllim.benchcalculator.lexer.SimpleLexer;
import com.alvinllim.benchcalculator.parser.Parser;
import com.alvinllim.benchcalculator.parser.ParserException;
import com.alvinllim.benchcalculator.parser.SimpleParser;
import com.alvinllim.benchcalculator.scanner.InputStringScanner;
import com.google.common.base.Joiner;

/**
 * Simple implementation of Unix bc (Bench Calculator) utility for Novus software test.
 * 
 * This implementation is currently limited to the following operators (in order of precedence):
 * <ol>
 * <li>binary operators * and /</li>
 * <li>binary operators + and -</li>
 * </ol>
 * 
 * The accepted input syntax is modeled in EBNF as follows:
 * <pre>
 * expression = term | expression , { ("+"|"-") , term } ;
 * term = number | term , { ("*"|"/") , number } ;
 * number = { digit }+ [ "." , { digit } ] ;
 * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
 * </pre>
 * 
 * Whitespaces are allowed anywhere between tokens, and will be ignored.
 * 
 * Additional operators (unary negative, power, etc) or features (parenthesis, multiple statements, variable assignments, boolean expressions, etc) can be implemented 
 * by extending {@code SimpleExpressionLexer} and {@code SimpleExpressionParser}.
 * 
 * @author alvinllim
 *
 */
public class SimpleBenchCalculator implements BenchCalculator {

	public static void main(String[] args) throws ParserException {
		
		//prepare input for scanning
		//join all arguments into a single string
		String input = Joiner.on("").join(args);
		
		//create a new SimpleBenchCalculator instance
		SimpleBenchCalculator bc = new SimpleBenchCalculator();
		
		//evaluate input
		Double result = bc.evaluate(input);
		
		//format result
		String output;
		if (result!=null && result==result.longValue())
			output = String.format("%d", result.longValue());
		else
			output = String.format("%s", result);
		
		//print result
		System.out.println(output);
		
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.BenchCalculator#evaluate(java.lang.String)
	 */
	@Override
	public Double evaluate(String input) throws ParserException {
		Parser parser = new SimpleParser(new SimpleLexer(new InputStringScanner(input)));
		Expression expr = parser.parse();
		return expr.evaluate();
	}
}
