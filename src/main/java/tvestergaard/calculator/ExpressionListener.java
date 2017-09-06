package tvestergaard.calculator;

import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.RuleNode;
import tvestergaard.calculator.antlr.CalculatorBaseListener;
import tvestergaard.calculator.antlr.CalculatorParser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionListener extends CalculatorBaseListener
{

	/**
	 * The state of the calculator.
	 */
	protected CalculatorState state;

	/**
	 * Creates a new expression listener.
	 *
	 * @param state The state of the calculator.
	 */
	public ExpressionListener(CalculatorState state)
	{
		this.state = state;
	}

	@Override public void enterExpression(ExpressionContext ctx)
	{
		VariableDeclarationContext variableDeclarationContext = ctx.variableDeclaration();
		if (variableDeclarationContext != null) {
			enterVariableDeclaration(variableDeclarationContext);
		}

		FunctionDeclarationContext functionDeclarationContext = ctx.functionDeclaration();
		if (functionDeclarationContext != null) {
			enterFunctionDeclaration(functionDeclarationContext);
		}

		ConstantDeclarationContext constantDeclarationContext = ctx.constantDeclaration();
		if (constantDeclarationContext != null) {
			enterConstantDeclaration(constantDeclarationContext);
		}

		if (ctx.COMMAND() != null) {
			enterCommand(ctx.getText());
		}

		MultiplicativeExpressionContext multiplicativeContext = ctx.multiplicativeExpression();
		if (multiplicativeContext != null) {
			ExpressionVisitor visitor = new ExpressionVisitor(this.state);
			Double            result  = visitor.visitMultiplicativeExpression(multiplicativeContext);
			System.out.println(String.format("%s %s %s", Colors.ANSI_GREEN, Double.toString(result), Colors.ANSI_RESET));
		}
	}

	/**
	 * Handles constant declarations.
	 *
	 * @param ctx The context of the constant declaration.
	 */
	public void enterConstantDeclaration(ConstantDeclarationContext ctx)
	{
		ExpressionVisitor expressionVisitor = new ExpressionVisitor(this.state);
		String            constantName      = ctx.IDENTIFIER().getText();
		Double            constantValue     = expressionVisitor.visitMultiplicativeExpression(ctx.multiplicativeExpression());

		this.state.memory.addConstant(constantName, constantValue);
	}

	/**
	 * Handles calculator commands.
	 *
	 * @param commandName The name of the command.
	 */
	private void enterCommand(String commandName)
	{
		if (commandName.equals("memory")) {
			printMemory();
			return;
		}

		if (commandName.equals("functions")) {
			printFunctions();
			return;
		}

		if (commandName.equals("reset")) {
			resetMemory();
			return;
		}

		throw new RuntimeException(String.format("Command '%s' hasValue not yet been implemented.", commandName));
	}

	/**
	 * Prints the defined variables to the screen.
	 */
	private void printMemory()
	{
		System.out.print(Colors.ANSI_CYAN);
		state.memory.iterate().forEachRemaining(entry -> {
			System.out.println(String.format(" - %s = %s", entry.getKey(), entry.getValue()));
		});
		System.out.print(Colors.ANSI_RESET);
	}

	/**
	 * Prints the defined functions to the string.
	 */
	private void printFunctions()
	{
		System.out.print(Colors.ANSI_CYAN);
		state.functions.iterate().forEachRemaining(function -> {
			System.out.println(String.format(" - %s", function.getSignature()));
		});
		System.out.print(Colors.ANSI_RESET);
	}

	/**
	 * Clears the memory of the calculator.
	 */
	private void resetMemory()
	{
		this.state.memory.reset();
	}

	/**
	 * Declare a new variable.
	 *
	 * @param ctx The context of the variable declaration.
	 */
	@Override public void enterVariableDeclaration(VariableDeclarationContext ctx)
	{
		String            variableName = ctx.IDENTIFIER().getText();
		ExpressionVisitor visitor      = new ExpressionVisitor(state);
		this.state.memory.addVariable(
				variableName,
				visitor.visitMultiplicativeExpression(ctx.multiplicativeExpression())
		);
	}

	/**
	 * Declare a new function.
	 *
	 * @param ctx The context of the function declaration.
	 */
	@Override public void enterFunctionDeclaration(FunctionDeclarationContext ctx)
	{
		FunctionSignatureContext        signatureContext  = ctx.functionSignature();
		FunctionParametersContext       parametersContext = signatureContext.functionParameters();
		MultiplicativeExpressionContext bodyContext       = ctx.multiplicativeExpression();
		String                          functionName      = signatureContext.IDENTIFIER().getText();
		List<String> parameters = parametersContext.IDENTIFIER().stream()
												   .map(parameter -> parameter.getText())
												   .collect(Collectors.toList());

		validateFunctionParameters(parameters, signatureContext.getText());
		CustomConcreteFunction customFunction = new CustomConcreteFunction(functionName, parameters, signatureContext, bodyContext);
		if (this.state.functions.has(customFunction)) {
			throw new RuntimeException(String.format("Function '%s' already exists.", signatureContext.getText()));
		}

		this.state.functions.add(customFunction);
	}

	/**
	 * Validates that no duplicate parameters exists in a list of strings.
	 *
	 * @param parameters The list of parameters to validate.
	 * @param signature  The signature of the function to check.
	 */
	private void validateFunctionParameters(List<String> parameters, String signature)
	{
		List<String> temp = new ArrayList<>();
		parameters.forEach(parameter -> {
			if (temp.contains(parameter)) {
				throw new RuntimeException(String.format("Duplicate parameter '%s' in function '%s'.", parameter, signature));
			}
			temp.add(parameter);
		});
	}
}
