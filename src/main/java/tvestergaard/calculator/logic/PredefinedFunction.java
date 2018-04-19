package tvestergaard.calculator.logic;

public abstract class PredefinedFunction extends AbstractFunction
{

    /**
     * The signature of the predefined function.
     */
    private final String signature;

    /**
     * Creates a new predefined function.
     *
     * @param name           The name of the function.
     * @param parameterCount The number of parameters in the function.
     * @param signature      The signature of the function.
     */
    public PredefinedFunction(String name, int parameterCount, String signature)
    {
        super(name, parameterCount);

        assert signature != null;
        this.signature = signature;
    }

    /**
     * Returns the signature of the function.
     *
     * @return The signature of the function.
     */
    @Override public String getSignature()
    {
        return this.signature;
    }
}
