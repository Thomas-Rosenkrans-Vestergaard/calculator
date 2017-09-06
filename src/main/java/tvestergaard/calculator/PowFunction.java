package tvestergaard.calculator;

import java.util.List;

public class PowFunction extends PredefinedConcreteFunction
{

	/**
	 * Creates the pow function.
	 */
	public PowFunction()
	{
		super("pow", 2, "pow(base, exponent)");
	}

	@Override public Double call(CalculatorState state, List<Double> arguments)
	{
		int argumentCount = arguments.size();
		if (argumentCount != 2) {
			throw new RuntimeException(String.format("The pow function takes 2 arguments, %d provided.", argumentCount));
		}

		return Math.pow(arguments.get(0), arguments.get(1));
	}
}
