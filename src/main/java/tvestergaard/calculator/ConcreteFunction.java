package tvestergaard.calculator;

import java.util.List;

public interface ConcreteFunction extends Function
{

	/**
	 * Calls the function.
	 *
	 * @param state     The state of the calculator.
	 * @param arguments The arguments to pass to the function.
	 * @return The result of the function call.
	 */
	Double call(CalculatorState state, List<Double> arguments);
}
