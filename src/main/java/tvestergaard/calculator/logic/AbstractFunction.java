package tvestergaard.calculator.logic;

public abstract class AbstractFunction implements Function
{

    /**
     * The name of the function.
     */
    private final String name;

    /**
     * The number of parameters of the function.
     */
    private final int parameterCount;

    /**
     * Creates a new {@link AbstractFunction}.
     *
     * @param name           The name of the function.
     * @param parameterCount The number of parameters of the function.
     */
    public AbstractFunction(String name, int parameterCount)
    {
        this.name = name;
        this.parameterCount = parameterCount;
    }

    /**
     * Returns the name of the function.
     *
     * @return The name of the function.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Returns the number of parameters in the function.
     *
     * @return The number of parameters in the function.
     */
    @Override public int getParameterCount()
    {
        return parameterCount;
    }

    /**
     * Returns the identifier used when identifying the function.
     *
     * @return The identifier used when identifying the function.
     */
    @Override public int getIdentifier()
    {
        int result = name.hashCode();
        result = 31 * result + parameterCount;
        return result;
    }
}
