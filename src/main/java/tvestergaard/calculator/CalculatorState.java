package tvestergaard.calculator;

public class CalculatorState
{

	/**
	 * The memory of the state.
	 */
	public final CalculatorMemory memory = new CalculatorMemory();

	/**
	 * The custom functions defined in the state.
	 */
	public final FunctionCollection functions = new FunctionCollection();

	/**
	 * Initialized predefined constants and functions.
	 */
	public CalculatorState()
	{
		this.functions.add(new AbsFunction());
		this.functions.add(new PowFunction());
	}
}