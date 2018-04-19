package tvestergaard.calculator.logic;

import java.util.List;

public interface Function extends FunctionIdentifier
{

    /**
     * Returns the name of the function.
     *
     * @return The name of the function.
     */
    String getName();

    /**
     * Returns the number of parameters in the function.
     *
     * @return The number of parameters in the function.
     */
    int getParameterCount();

    /**
     * Returns the signature of the function.
     *
     * @return The signature of the function.
     */
    String getSignature();

    /**
     * Calls the function.
     *
     * @param visitor   The visitor that is used when executing the function.
     * @param memory    The memory of the calculator.
     * @param arguments The arguments to pass to the function.
     * @return The result of the function call.
     */
    ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments);
}
