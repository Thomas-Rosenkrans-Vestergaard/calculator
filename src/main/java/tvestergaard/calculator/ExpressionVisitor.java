package tvestergaard.calculator;

import org.antlr.v4.runtime.Parser;
import tvestergaard.calculator.antlr.CalculatorBaseVisitor;
import tvestergaard.calculator.antlr.CalculatorParser.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionVisitor extends CalculatorBaseVisitor<Double>
{

	/**
	 * The state of the calculator.
	 */
	protected CalculatorState state;

	/**
	 * Creates a new expression visitor.
	 *
	 * @param state The state of the calculator.
	 */
	public ExpressionVisitor(CalculatorState state)
	{
		this.state = state;
	}

	@Override public Double visitMultiplicativeExpression(MultiplicativeExpressionContext ctx)
	{
		MultiplicativeOperatorContext operatorContext = ctx.multiplicativeOperator();
		if (operatorContext != null) {

			String operator = operatorContext.getText();

			if (operator.equals("*")) {

				Double left  = visitMultiplicativeExpression(ctx.multiplicativeExpression());
				Double right = visitAdditiveExpression(ctx.additiveExpression());

				return left * right;
			}

			if (operator.equals("/")) {
				Double left  = visitMultiplicativeExpression(ctx.multiplicativeExpression());
				Double right = visitAdditiveExpression(ctx.additiveExpression());

				if (right == 0) {
					throw new RuntimeException("You cannot divide by 0.");
				}

				return left / right;
			}
		}

		AdditiveExpressionContext additiveExpressionContext = ctx.additiveExpression();
		if (additiveExpressionContext != null) {
			return visitAdditiveExpression(additiveExpressionContext);
		}

		throw new IllegalStateException(String.format("Illegal state when solving a multiplicative expression '%s'.", ctx.getText()));
	}

	@Override public Double visitAdditiveExpression(AdditiveExpressionContext ctx)
	{

		AdditiveOperatorContext operatorContext = ctx.additiveOperator();
		if (operatorContext != null) {

			String operator = operatorContext.getText();
			if (operator.equals("+")) {
				Double left  = visitAdditiveExpression(ctx.additiveExpression());
				Double right = visitPrimaryExpression(ctx.primaryExpression());

				return left + right;
			}

			if (operator.equals("-")) {
				Double left  = visitAdditiveExpression(ctx.additiveExpression());
				Double right = visitPrimaryExpression(ctx.primaryExpression());

				return left - right;
			}
		}

		if (ctx.getChildCount() == 1) {
			PrimaryExpressionContext primaryExpressionContext = ctx.primaryExpression();
			return visitPrimaryExpression(primaryExpressionContext);
		}

		throw new IllegalStateException(String.format("Illegal state when solving an additive expression '%s'.", ctx.getText()));
	}

	@Override public Double visitPrimaryExpression(PrimaryExpressionContext ctx)
	{
		ParenthesizedExpressionContext parenthesizedExpressionContext = ctx.parenthesizedExpression();
		if (parenthesizedExpressionContext != null) {
			return visitMultiplicativeExpression(parenthesizedExpressionContext.multiplicativeExpression());
		}

		FunctionExpressionContext functionExpressionContext = ctx.functionExpression();
		if (functionExpressionContext != null) {
			return visitFunctionExpression(functionExpressionContext);
		}

		if (ctx.VALUE_FLOAT() != null || ctx.VALUE_INT() != null) {
			return Double.parseDouble(ctx.getText());
		}

		if (ctx.IDENTIFIER() != null) {
			return getVariableValue(ctx.getText());
		}

		throw new IllegalStateException("Illegal state when solving a primary expression.");
	}

	@Override public Double visitFunctionExpression(FunctionExpressionContext ctx)
	{
		String       functionName = ctx.IDENTIFIER().getText();
		List<Double> arguments    = getArguments(ctx.functionArguments());
		BaseFunction function     = new BaseFunction(functionName, arguments.size());

		if (this.state.functions.has(function)) {
			return this.state.functions.get(function).call(this.state, arguments);
		}

		throw new IllegalStateException(String.format("Illegal state when calling function '%s'.", functionName));
	}

	private List<Double> getArguments(FunctionArgumentsContext ctx)
	{
		List<Double> result = new ArrayList<>();
		for (MultiplicativeExpressionContext multiplicativeExpressionContext : ctx.multiplicativeExpression()) {
			result.add(visitMultiplicativeExpression(multiplicativeExpressionContext));
		}

		return result;
	}

	/**
	 * Returns the value of a variable from memory.
	 *
	 * @param name The name of the variable.
	 * @return The value of the variable.
	 */
	private Double getVariableValue(String name)
	{
		if (!this.state.memory.has(name)) {
			throw new RuntimeException(String.format("No variable with name '%s'.", name));
		}

		return this.state.memory.get(name);
	}
}
