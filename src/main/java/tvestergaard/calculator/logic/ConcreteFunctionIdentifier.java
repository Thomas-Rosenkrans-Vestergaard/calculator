package tvestergaard.calculator.logic;

public class ConcreteFunctionIdentifier implements FunctionIdentifier
{

    /**
     * The name of the function being identified.
     */
    private final String functionName;

    /**
     * The number of argument of the function being identified.
     */
    private final int parameterCount;

    /**
     * Creates a new {@link ConcreteFunctionIdentifier}.
     *
     * @param functionName   The name of the function being identified.
     * @param parameterCount The number of argument of the function being identified.
     */
    public ConcreteFunctionIdentifier(String functionName, int parameterCount)
    {
        this.functionName = functionName;
        this.parameterCount = parameterCount;
    }


    /**
     * Creates and returns a new {@link ConcreteFunctionIdentifier}.
     *
     * @param functionName   The name of the function being identified.
     * @param parameterCount The number of argument of the function being identified.
     * @return The newly created instance of {@link ConcreteFunctionIdentifier}.
     */
    public static FunctionIdentifier of(String functionName, int parameterCount)
    {
        return new ConcreteFunctionIdentifier(functionName, parameterCount);
    }

    /**
     * Returns the identifier used when identifying the function.
     *
     * @return The identifier used when identifying the function.
     */
    @Override public int getIdentifier()
    {
        int result = functionName.hashCode();
        result = 31 * result + parameterCount;
        return result;
    }
}
