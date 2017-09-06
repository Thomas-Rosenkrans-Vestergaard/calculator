package tvestergaard.calculator;

import java.util.List;

public class AbsFunction extends PredefinedConcreteFunction
{

	/**
	 * Creates a new abs function.
	 */
	public AbsFunction()
	{
		super("abs", 1, "abs(value)");
	}

	/**
	 * Calls the abs function.
	 *
	 * @param state     The state of the calculator.
	 * @param arguments The arguments to pass to the function.
	 * @return
	 */
	@Override public Double call(CalculatorState state, List<Double> arguments)
	{
		if (arguments.size() < 1) {
			throw new RuntimeException("Function abs takes one argument.");
		}

		return Math.abs(arguments.get(0));
	}
}
