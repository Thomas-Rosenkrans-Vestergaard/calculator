package tvestergaard.calculator;

import org.antlr.v4.runtime.*;
import tvestergaard.calculator.antlr.CalculatorLexer;
import tvestergaard.calculator.antlr.CalculatorParser;

import java.util.Scanner;

public class Calculator
{

	public static void main(String[] args) throws Exception
	{
		Scanner            scanner         = new Scanner(System.in);
		CalculatorState    calculatorState = new CalculatorState();
		ExpressionListener listener        = new ExpressionListener(calculatorState);

		System.out.println("Tell me what to calculate.");

		while (true) {
			try {
				String            input      = scanner.nextLine();
				CharStream        charStream = CharStreams.fromString(input);
				CalculatorLexer   lexer      = new CalculatorLexer(charStream);
				CommonTokenStream tokens     = new CommonTokenStream(lexer);
				CalculatorParser  parser     = new CalculatorParser(tokens);
				parser.removeErrorListeners();
				parser.addErrorListener(new BaseErrorListener()
				{
					@Override public void syntaxError(
							Recognizer<?, ?> recognizer,
							Object offendingSymbol,
							int line,
							int charPositionInLine,
							String msg,
							RecognitionException e)
					{
						throw new RuntimeException(String.format("%s %s %s", Colors.ANSI_RED, msg, Colors.ANSI_RESET));
					}
				});

				CalculatorParser.ExpressionContext expressionContext = parser.expression();
				listener.enterExpression(expressionContext);

			} catch (RuntimeException e) {
				printError(e);
			}
		}
	}

	private static void printError(RuntimeException e)
	{
		System.out.println(
				String.format(
						"%s %s %s",
						Colors.ANSI_RED,
						e.getMessage(),
						Colors.ANSI_RESET
				)
		);
	}
}
