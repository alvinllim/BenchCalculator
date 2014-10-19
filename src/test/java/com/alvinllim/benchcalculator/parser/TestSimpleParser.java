package com.alvinllim.benchcalculator.parser;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alvinllim.benchcalculator.expression.Addition;
import com.alvinllim.benchcalculator.expression.Division;
import com.alvinllim.benchcalculator.expression.Expression;
import com.alvinllim.benchcalculator.expression.Multiplication;
import com.alvinllim.benchcalculator.expression.NumberLiteral;
import com.alvinllim.benchcalculator.expression.Subtraction;
import com.alvinllim.benchcalculator.lexer.LexerException;
import com.alvinllim.benchcalculator.lexer.SimpleLexer;
import com.alvinllim.benchcalculator.scanner.InputStringScanner;

public class TestSimpleParser {

	@DataProvider(name="dpLiteral")
	public Object[][] dataProviderLiteral() {
		return new Object[][] {
				new Object[]{"123"},
				new Object[]{"123.456"}
		};
	}

	@Test(dataProvider="dpLiteral")
	public void parse_Literal(String input) throws ParserException {
		SimpleParser parser = new SimpleParser(new SimpleLexer(new InputStringScanner(input)));
		Expression expr = parser.parse();
		Assert.assertEquals(expr.getClass(), NumberLiteral.class);
	}

	@DataProvider(name="dpPlusMinus")
	public Object[][] dataProviderPlusMinus() {
		return new Object[][] {
				new Object[]{"1+2",Addition.class},
				new Object[]{" 2 - 3 ",Subtraction.class},
				new Object[]{" 1*2 + 3 ",Addition.class},
				new Object[]{" 2 - 3 / 2 + 4",Addition.class}
		};
	}

	@Test(dataProvider="dpPlusMinus")
	public void parse_PlusMinus(String input, Class<?> expectedClass) throws ParserException {
		SimpleParser parser = new SimpleParser(new SimpleLexer(new InputStringScanner(input)));
		Expression expr = parser.parse();
		Assert.assertEquals(expr.getClass(), expectedClass);
	}

	@DataProvider(name="dpMultiplyDivide")
	public Object[][] dataProviderMultiplyDivide() {
		return new Object[][] {
				new Object[]{"1*2",Multiplication.class},
				new Object[]{" 2 / 3 ",Division.class},
				new Object[]{" 1*2 / 3 ",Division.class},
				new Object[]{" 2 / 3 / 2 / 4",Division.class}
		};
	}

	@Test(dataProvider="dpMultiplyDivide")
	public void parse_MultiplyDivide(String input, Class<?> expectedClass) throws ParserException {
		SimpleParser parser = new SimpleParser(new SimpleLexer(new InputStringScanner(input)));
		Expression expr = parser.parse();
		Assert.assertEquals(expr.getClass(), expectedClass);
	}
	
	@Test(dataProvider="dpLiteral")
	public void parseFactor_Literal(String input) throws LexerException, ParserException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		SimpleParser parser = new SimpleParser(lexer);
		Expression expr = parser.parseTerm();
		Assert.assertEquals(expr.getClass(), NumberLiteral.class);
	}

	@Test(dataProvider="dpMultiplyDivide")
	public void parseFactor_MultiplyDivide(String input, Class<?> expectedClass) throws LexerException, ParserException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		SimpleParser parser = new SimpleParser(lexer);
		Expression expr = parser.parseTerm();
		Assert.assertEquals(expr.getClass(), expectedClass);
	}
}
