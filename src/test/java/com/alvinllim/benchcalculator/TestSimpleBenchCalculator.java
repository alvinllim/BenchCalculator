package com.alvinllim.benchcalculator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alvinllim.benchcalculator.parser.ParserException;

public class TestSimpleBenchCalculator {

	@DataProvider(name="dpEvaluate")
	public Object[][] dataProviderEvaluate() {
		return new Object[][] {
				new Object[] {"", null},
				new Object[] {"123.4", 123.4},
				new Object[] {"1+4", Double.valueOf(5)},
				new Object[] {" 1 + 1 ", Double.valueOf(2)},
				new Object[] {"1-1", Double.valueOf(0)},
				new Object[] {"2*3", Double.valueOf(6)},
				new Object[] {"9 / 3", Double.valueOf(3)},
				new Object[] {"1 + 2 * 3", Double.valueOf(7)}, //multiplication operator precedence
				new Object[] {"5 * 4+2", Double.valueOf(22)}, //multiplication operator precedence
				new Object[] {"5 + 4 / 2", Double.valueOf(7)}, //division operator precedence
				new Object[] {"5 / 2", Double.valueOf(2)}, //division results rounded down by bc
				new Object[] {"1.23 * 3", 3.69}, //multiplication results not rounded down by bc
				new Object[] {"5 / 2 * 2", Double.valueOf(4)} //bc exhibits left precedence for multiplication/division, and division is rounded down
		};
	}

	@Test(dataProvider="dpEvaluate")
	public void evaluate(String input, Double output) throws ParserException {
		BenchCalculator bc = new SimpleBenchCalculator();
		Assert.assertEquals(bc.evaluate(input), output);
	}

	@DataProvider(name="dpMain")
	public Object[][] dataProviderMain() {
		return new Object[][] {
				new Object[] {new String[]{""}},
				new Object[] {new String[]{"123.4"}},
				new Object[] {new String[]{"1","+","4"}},
				new Object[] {new String[]{"1"," ","+"," ","1 "}},
				new Object[] {new String[]{"1-1"}},
				new Object[] {new String[]{"2*3"}},
				new Object[] {new String[]{"9"," ","/"," ","3"}},
				new Object[] {new String[]{"1"," ","+"," ","2"," ","*"," 3"}}, //multiplication operator precedence
				new Object[] {new String[]{"5"," ","*"," ","4+2"}}, //multiplication operator precedence
				new Object[] {new String[]{"5"," ","+"," ","4","/"," ","2"}}, //division operator precedence
				new Object[] {new String[]{"5"," ","/"," ","2"}}, //division results rounded down by bc
				new Object[] {new String[]{"1.23"," ","*"," ","3"}}, //multiplication results not rounded down by bc
				new Object[] {new String[]{"5"," ","/"," ","2"," ","*"," ","2"}} //bc exhibits left precedence for multiplication/division, and division is rounded down
		};
	}

	@Test(dataProvider="dpMain")
	public void main(String[] input) throws ParserException {
		SimpleBenchCalculator.main(input);
	}
}
