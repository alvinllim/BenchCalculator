package com.alvinllim.benchcalculator.parser;

import com.alvinllim.benchcalculator.expression.Addition;
import com.alvinllim.benchcalculator.expression.BlankExpression;
import com.alvinllim.benchcalculator.expression.Division;
import com.alvinllim.benchcalculator.expression.Expression;
import com.alvinllim.benchcalculator.expression.Multiplication;
import com.alvinllim.benchcalculator.expression.NumberLiteral;
import com.alvinllim.benchcalculator.expression.Subtraction;
import com.alvinllim.benchcalculator.expression.Token;
import com.alvinllim.benchcalculator.expression.TokenType;
import com.alvinllim.benchcalculator.lexer.Lexer;
import com.alvinllim.benchcalculator.lexer.LexerException;

/**
 * Simple implementation of {@code com.alvinllim.benchcalculator.parser.Parser} for Novus software test.
 * 
 * This implementation invokes a recursive descent algorithm to parse simple mathematical expressions which are limited to the following operators (in order of precedence):
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
public class SimpleParser implements Parser {

	public static final String OPERATOR_PLUS = "+";
	public static final String OPERATOR_MINUS = "-";
	public static final String OPERATOR_TIMES = "*";
	public static final String OPERATOR_DIVIDE = "/";

	private Lexer lexer;
	protected Token currentToken;
	protected Token nextToken;

	public SimpleParser(Lexer lexer) throws ParserException {
		this.lexer = lexer;
		try {
			//initialize pointers to current and next tokens
			currentToken = lexer.nextToken();
			nextToken = lexer.nextToken();
		} catch (LexerException e) {
			throw new ParserException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.alvinllim.benchcalculator.parser.Parser#parse()
	 */
	@Override
	public Expression parse() throws ParserException {
		return parseExpression();
	}

	protected void getNextToken() throws LexerException {
		currentToken = nextToken;
		nextToken = lexer.nextToken();
	}

	/**
	 * expression = term | expression , { ("+"|"-") , term } ;
	 * 
	 * @return parsed expression
	 * @throws ParserException 
	 */
	public Expression parseExpression() throws ParserException {
		try {
			Expression leftExpression = parseTerm();
			getNextToken(); //set currentToken to next plus/minus operator (or EOL)
			return parseExpression(leftExpression);

		} catch (LexerException e) {
			throw new ParserException(e);
		}
	}

	/**
	 * expression = term | expression , { ("+"|"-") , term } ;
	 * 
	 * @param leftExpression for recursive ascent parsing
	 * @return parsed expression
	 * @throws ParserException 
	 */
	protected Expression parseExpression(Expression leftExpression) throws ParserException {
		Expression expression;
		try {
			//check if there is a RHS
			if (currentToken.getType() == TokenType.EOL) { //no RHS
				expression = leftExpression;

			} else if (currentToken.getType() == TokenType.NUMBER_OPERATOR && 
					(OPERATOR_PLUS.equals(currentToken.getValue()) || OPERATOR_MINUS.equals(currentToken.getValue()))) { //add or subtract RHS
				Token operatorToken = currentToken;
				getNextToken(); //set currentToken to RHS
				Expression rhs = parseTerm();

				Expression nextLeftExpression;
				if (OPERATOR_PLUS.equals(operatorToken.getValue())) {
					nextLeftExpression = new Addition(leftExpression, rhs);
				} else if (OPERATOR_MINUS.equals(operatorToken.getValue())) {
					nextLeftExpression = new Subtraction(leftExpression, rhs);
				} else { //invalid operator
					throw new ParserException(operatorToken);
				}

				getNextToken(); //set currentToken to next plus/minus operator (or EOL)
				expression = parseExpression(nextLeftExpression);

			} else { //invalid current token
				throw new ParserException(currentToken);
			}
			return expression;

		} catch (LexerException e) {
			throw new ParserException(e);
		}
	}

	/**
	 * term = number | term , { ("*"|"/") , number } ;
	 * 
	 * @return parsed factor expression
	 * @throws ParserException 
	 */
	protected Expression parseTerm() throws ParserException {
		//check if this is a blank expression
		if (currentToken.getType() == TokenType.EOL) {
			return new BlankExpression();
		}

		//LHS needs to be a number
		if (currentToken.getType() != TokenType.NUMBER) {
			throw new ParserException(currentToken);
		} else {
			//get LHS of expression
			Expression lhs = new NumberLiteral(currentToken.getValue());
			return parseTerm(lhs);
		}
	}

	/**
	 * term = number | term , { ("*"|"/") , number } ;
	 * 
	 * @param leftTerm for recursive ascent parsing
	 * @return parsed factor expression
	 * @throws ParserException 
	 */
	protected Expression parseTerm(Expression leftTerm) throws ParserException {
		Expression term;
		try {
			//check if there is a RHS
			if (nextToken.getType() == TokenType.EOL) { //no RHS
				term = leftTerm;

			} else if (nextToken.getType() == TokenType.NUMBER_OPERATOR && 
					(OPERATOR_PLUS.equals(nextToken.getValue()) || OPERATOR_MINUS.equals(nextToken.getValue()))) { //plus and minus operators needs to be ignored by this method, since they belong to the outer expression
				term = leftTerm;

			} else if (nextToken.getType() == TokenType.NUMBER_OPERATOR && 
					(OPERATOR_TIMES.equals(nextToken.getValue()) || OPERATOR_DIVIDE.equals(nextToken.getValue()))) { //multiply or divide by RHS
				getNextToken(); //set currentToken to operator
				Token operatorToken = currentToken;
				getNextToken(); //set currentToken to RHS

				//RHS needs to be a number
				Expression rhs;
				if (currentToken.getType() != TokenType.NUMBER) {
					throw new ParserException(currentToken);
				} else {
					rhs = new NumberLiteral(currentToken.getValue());
				}

				Expression nextLeftTerm;
				if (OPERATOR_TIMES.equals(operatorToken.getValue())) {
					nextLeftTerm = new Multiplication(leftTerm, rhs);
				} else if (OPERATOR_DIVIDE.equals(operatorToken.getValue())) {
					nextLeftTerm = new Division(leftTerm, rhs);
				} else { //invalid operator
					throw new ParserException(operatorToken);
				}
				term = parseTerm(nextLeftTerm);

			} else { //invalid current token
				throw new ParserException(nextToken);
			}
			return term;
			
		} catch (LexerException e) {
			throw new ParserException(e);
		}
	}

}
