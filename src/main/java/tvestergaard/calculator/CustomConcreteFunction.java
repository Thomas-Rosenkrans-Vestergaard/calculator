package tvestergaard.calculator;

import tvestergaard.calculator.antlr.CalculatorParser.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomConcreteFunction extends BaseConcreteFunction
{

	/**
	 * The parameters that the function accepts.
	 */
	private List<String> parameters;

	/**
	 * The context of the CustomConcreteFunction signature.
	 */
	private FunctionSignatureContext signatureContext;

	/**
	 * The context of the function body.
	 */
	private MultiplicativeExpressionContext bodyContext;

	/**
	 * Creates a new custom function.
	 *
	 * @param name             The name of the function.
	 * @param parameters       The parameters of the function.
	 * @param signatureContext The signature context.
	 * @param bodyContext      The body context.
	 */
	public CustomConcreteFunction(String name, List<String> parameters, FunctionSignatureContext signatureContext, MultiplicativeExpressionContext bodyContext)
	{
		super(name, parameters.size());

		assert parameters != null;
		assert signatureContext != null;
		assert bodyContext != null;

		this.parameters = parameters;
		this.signatureContext = signatureContext;
		this.bodyContext = bodyContext;
	}

	@Override public Double call(CalculatorState state, List<Double> arguments)
	{
		if (arguments.size() != this.parametersCount) {
			throw new RuntimeException(String.format("Function '%s' takes %d arguments, %d provided.", getSignature(), this.parametersCount, arguments.size()));
		}

		Map<String, Double> namedArguments = new HashMap<>();
		Iterator<String>    i1             = this.parameters.iterator();
		Iterator<Double>    i2             = arguments.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			namedArguments.put(i1.next(), i2.next());
		}

		ExpressionVisitor visitor = new ExpressionVisitor(state);
		state.memory.enterScope(namedArguments);
		Double result = visitor.visitMultiplicativeExpression(this.bodyContext);
		state.memory.exitScope();
		return result;
	}

	@Override public String getSignature()
	{
		return this.signatureContext.getText();
	}
}
