package tvestergaard.calculator;

public abstract class BaseConcreteFunction extends BaseFunction implements ConcreteFunction
{

    /**
     * Creates a new concrete function.
     *
     * @param name           The name of the function.
     * @param parameterCount The parameters the function accepts.
     */
    public BaseConcreteFunction(String name, int parameterCount)
    {
        super(name, parameterCount);
    }
}
