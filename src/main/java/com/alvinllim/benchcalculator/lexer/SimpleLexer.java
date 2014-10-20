package com.alvinllim.benchcalculator.lexer;

import com.alvinllim.benchcalculator.expression.Token;
import com.alvinllim.benchcalculator.expression.TokenType;
import com.alvinllim.benchcalculator.scanner.InputScanner;

/**
 * Simple implementation of {@code com.alvinllim.benchcalculator.lexer.Lexer} for Novus software test.
 * 
 * This implementation retrieves tokens for simple mathematical expressions, and is limited to the following operators (in order of precedence):
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
 * Additional features (parenthesis, negative, power, etc) or features (multiple statements, variable assignments, boolean operators, etc) can be implemented 
 * by extending this class.
 * 
 * @author alvinllim
 *
 */
public class SimpleLexer implements Lexer {
	
	private InputScanner scanner;
	protected char currentCharacter;
	protected char nextCharacter;

	public SimpleLexer(InputScanner scanner) {
		this.scanner = scanner;
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.lexer.Lexer#nextToken()
	 */
	@Override
	public Token nextToken() throws LexerException {
		Token token = null;
		getNextCharacter();
		
		if ((token=getNumberOperator()) != null) {
			return token;
		}
		
		if ((token=getNumber()) != null) {
			return token;
		}
		
		if ((token=getEOL()) != null) {
			return token;
		}
		
		//throw exception since the current character does not match any acceptable token
		throw new LexerException(currentCharacter, scanner.getCurrentIndex());
	}
	
	protected void getNextCharacter() {
		if (scanner.hasMoreCharacters()) {
			currentCharacter = scanner.getNextCharacter();
			nextCharacter = scanner.lookahead(1);

			//recursively ignore whitespace
			if (isWhitespace(currentCharacter)) {
				getNextCharacter();
			}
		} else {
			//set current character to EOL
			currentCharacter = nextCharacter;
		}
	}
	
	protected Token getNumberOperator() {
		if (isNumberOperator(currentCharacter)) {
			return new Token(Character.toString(currentCharacter), TokenType.NUMBER_OPERATOR, scanner.getCurrentIndex());
		} else {
			return null;
		}
	}
	
	protected Token getNumber() throws LexerException {
		if (isDigit(currentCharacter)) {
			StringBuilder numberSB = new StringBuilder(Character.toString(currentCharacter));
			boolean hasDecimalPoint = false;
			while (isDigit(nextCharacter) || (isDecimalPoint(nextCharacter) && !hasDecimalPoint)) {
				if (isDecimalPoint(nextCharacter)) {
					hasDecimalPoint = true;
				}
				getNextCharacter();
				numberSB.append(currentCharacter);
			}
			//a number can only be followed by a whitespace, a number operator, or an EOF character
			if (!isWhitespace(nextCharacter) && !isNumberOperator(nextCharacter) && !isEOF(nextCharacter)) {
				throw new LexerException(nextCharacter, scanner.getCurrentIndex()+1);
			} else {
				return new Token(numberSB.toString(), TokenType.NUMBER, scanner.getCurrentIndex());
			}
		} else {
			return null;
		}
	}
	
	protected Token getEOL() {
		if (isEOF(currentCharacter)) {
			return new Token(Character.toString(currentCharacter), TokenType.EOL, scanner.getCurrentIndex());
		} else {
			return null;
		}
	}
	
	protected boolean isWhitespace(char character) {
		return character == ' ';
	}
	
	protected boolean isNumberOperator(char character) {
		return character == '+' 
				|| character == '-' 
				|| character == '*' 
				|| character == '/';
	}
	
	protected boolean isDigit(char character) {
		return character == '1' 
				|| character == '2' 
				|| character == '3' 
				|| character == '4'
				|| character == '5' 
				|| character == '6' 
				|| character == '7'
				|| character == '8' 
				|| character == '9' 
				|| character == '0';
	}
	
	protected boolean isDecimalPoint(char character) {
		return character == '.';
	}
	
	protected boolean isEOF(char character) {
		return character == 0;
	}

}
