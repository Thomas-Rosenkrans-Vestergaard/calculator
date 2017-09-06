package tvestergaard.calculator;

public class BaseFunction implements Function
{

	/**
	 * The name of the function.
	 */
	public final String name;

	/**
	 * The parameters in the function.
	 */
	public final int parametersCount;

	/**
	 * Creates a new base function.
	 *
	 * @param name             The name of the function.
	 * @param parametersCount The number of parameters in the function.
	 */
	public BaseFunction(String name, int parametersCount)
	{
		assert name != null;

		this.name = name;
		this.parametersCount = parametersCount;
	}

	@Override public String getName()
	{
		return this.name;
	}

	@Override public int getParameterCount()
	{
		return this.parametersCount;
	}

	@Override public String getSignature()
	{
		return String.format("%s(%d)", this.name, this.parametersCount);
	}

	@Override public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseConcreteFunction that = (BaseConcreteFunction) o;

		if (getParameterCount() != that.getParameterCount()) return false;
		return name.equals(that.name);
	}

	@Override public int hashCode()
	{
		int result = name.hashCode();
		result = 31 * result + getParameterCount();
		return result;
	}
}
