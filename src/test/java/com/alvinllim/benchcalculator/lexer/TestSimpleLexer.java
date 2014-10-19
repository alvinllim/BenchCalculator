package com.alvinllim.benchcalculator.lexer;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alvinllim.benchcalculator.expression.Token;
import com.alvinllim.benchcalculator.expression.TokenType;
import com.alvinllim.benchcalculator.lexer.SimpleLexer;
import com.alvinllim.benchcalculator.scanner.InputStringScanner;

public class TestSimpleLexer {

	@Test
	public void testGetCharacter() {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner("abcde"));
		lexer.getNextCharacter();
		Assert.assertEquals(lexer.currentCharacter, 'a');
		Assert.assertEquals(lexer.nextCharacter, 'b');
		lexer.getNextCharacter();
		lexer.getNextCharacter();
		Assert.assertEquals(lexer.currentCharacter, 'c');
		Assert.assertEquals(lexer.nextCharacter, 'd');
	}

	@DataProvider(name="dpEOL")
	public Object[][] dataProviderEOL() {
		return new Object[][] {
				new Object[]{""}
		};
	}

	@Test(dataProvider="dpEOL")
	public void testGetEOL(String input) {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		lexer.getNextCharacter();
		Token token = lexer.getEOL();
		Assert.assertNotNull(token);
		Assert.assertEquals(token.getType(), TokenType.EOL);
	}

	@DataProvider(name="dpValidNumber")
	public Object[][] dataProviderValidNumber() {
		return new Object[][] {
				new Object[]{"123456","123456"},
				new Object[]{"123.456","123.456"},
				new Object[]{" 123","123"},
				new Object[]{"123 ","123"},
				new Object[]{"123+abc","123"},
				new Object[]{"123.45 abc","123.45"},
				new Object[]{"123 456","123"},
				new Object[]{"1234 .56","1234"}
		};
	}
	
	@Test(dataProvider="dpValidNumber")
	public void testGetNumber_WhenValid_ThenReturnToken(String input, String expected) throws LexerException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		lexer.getNextCharacter();
		Token token = lexer.getNumber();
		Assert.assertNotNull(token);
		Assert.assertEquals(token.getType(), TokenType.NUMBER);
		Assert.assertEquals(token.getValue(), expected);
	}

	@DataProvider(name="dpNotNumber")
	public Object[][] dataProviderNotNumber() {
		return new Object[][] {
				new Object[]{"abc123"},
				new Object[]{"+123"},
				new Object[]{" +123"}
		};
	}

	@Test(dataProvider="dpNotNumber")
	public void testGetNumber_WhenNotNumber_ThenReturnNull(String input) throws LexerException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		lexer.getNextCharacter();
		Token token = lexer.getNumber();
		Assert.assertNull(token);
	}

	@DataProvider(name="dpInvalidNumber")
	public Object[][] dataProviderInvalidNumber() {
		return new Object[][] {
				new Object[]{"123abc"},
				new Object[]{"123(456)"},
				new Object[]{"123^2"}
		};
	}

	@Test(dataProvider="dpInvalidNumber", expectedExceptions=LexerException.class)
	public void testGetNumber_WhenInvalidNumber_ThenThrowLexerException(String input) throws LexerException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		lexer.getNextCharacter();
		Token token = lexer.getNumber();
		Assert.assertNull(token);
	}

	@DataProvider(name="dpNumberOperator")
	public Object[][] dataProviderNumberOperator() {
		return new Object[][] {
				new Object[]{"+","+"},
				new Object[]{"-","-"},
				new Object[]{"*","*"},
				new Object[]{"/","/"},
				new Object[]{"+123","+"},
				new Object[]{" +123","+"},
				new Object[]{"- 123","-"},
				new Object[]{"-+","-"}
		};
	}

	@Test(dataProvider="dpNumberOperator")
	public void testGetNumberOperator(String input, String expected) {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		lexer.getNextCharacter();
		Token token = lexer.getNumberOperator();
		Assert.assertNotNull(token);
		Assert.assertEquals(token.getType(), TokenType.NUMBER_OPERATOR);
		Assert.assertEquals(token.getValue(), expected);
	}

	@Test
	public void testIsDecimalPoint() {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(""));
		Assert.assertTrue(lexer.isDecimalPoint('.'));
		Assert.assertFalse(lexer.isDecimalPoint('o'));
	}

	@Test
	public void testIsDigit() {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(""));
		Assert.assertTrue(lexer.isDigit('1'));
		Assert.assertTrue(lexer.isDigit('2'));
		Assert.assertTrue(lexer.isDigit('3'));
		Assert.assertTrue(lexer.isDigit('4'));
		Assert.assertTrue(lexer.isDigit('5'));
		Assert.assertTrue(lexer.isDigit('6'));
		Assert.assertTrue(lexer.isDigit('7'));
		Assert.assertTrue(lexer.isDigit('8'));
		Assert.assertTrue(lexer.isDigit('9'));
		Assert.assertTrue(lexer.isDigit('0'));
		Assert.assertFalse(lexer.isDigit('o'));
	}

	@Test
	public void testIsEOF() {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(""));
		Assert.assertTrue(lexer.isEOF((char)0));
		Assert.assertFalse(lexer.isEOF('\n'));
	}

	@Test
	public void testIsNumberOperator() {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(""));
		Assert.assertTrue(lexer.isNumberOperator('+'));
		Assert.assertTrue(lexer.isNumberOperator('-'));
		Assert.assertTrue(lexer.isNumberOperator('*'));
		Assert.assertTrue(lexer.isNumberOperator('/'));
		Assert.assertFalse(lexer.isNumberOperator('='));
	}

	@DataProvider(name="dpValidNextToken")
	public Object[][] dataProviderValidNextToken() {
		return new Object[][] {
				new Object[]{"1","1",TokenType.NUMBER},
				new Object[]{"1+1","1",TokenType.NUMBER},
				new Object[]{" 1 +1","1",TokenType.NUMBER},
				new Object[]{"-1","-",TokenType.NUMBER_OPERATOR},
				new Object[]{"",Character.toString((char)0),TokenType.EOL},
		};
	}

	@Test(dataProvider="dpValidNextToken")
	public void testNextToken_WhenValid_ThenReturnToken(String input, String expected, TokenType tokenType) throws LexerException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		Token token = lexer.nextToken();
		Assert.assertNotNull(token);
		Assert.assertEquals(token.getValue(), expected);
		Assert.assertEquals(token.getType(), tokenType);
	}

	@DataProvider(name="dpInvalidNextToken")
	public Object[][] dataProviderInvalidNextToken() {
		return new Object[][] {
				new Object[]{"abc123"},
				new Object[]{".123"},
				new Object[]{" ^2"},
				new Object[]{"123a"}
		};
	}

	@Test(dataProvider="dpInvalidNextToken", expectedExceptions=LexerException.class)
	public void testNextToken_WhenInvalid_ThenThrowLexerException(String input) throws LexerException {
		SimpleLexer lexer = new SimpleLexer(new InputStringScanner(input));
		Token token = lexer.nextToken();
		Assert.fail("LexerException should have been thrown by now. "+token);
	}
}
