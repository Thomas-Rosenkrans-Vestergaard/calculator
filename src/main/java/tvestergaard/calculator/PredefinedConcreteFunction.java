package tvestergaard.calculator;

public abstract class PredefinedConcreteFunction extends BaseConcreteFunction
{

	/**
	 * The signature of the predefined function.
	 */
	private final String functionSignature;

	/**
	 * Creates a new predefined function.
	 *
	 * @param name              The name of the function.
	 * @param
	 * @param functionSignature The signature of the function.
	 */
	public PredefinedConcreteFunction(String name, int parameterLength, String functionSignature)
	{
		super(name, parameterLength);

		assert functionSignature != null;
		this.functionSignature = functionSignature;
	}

	@Override public String getSignature()
	{
		return this.functionSignature;
	}
}
