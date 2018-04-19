package tvestergaard.calculator.logic;

import java.util.List;

/**
 * Represents a function defined in source code.
 */
public interface CustomFunction extends Function
{

    /**
     * Returns the parameters of the function.
     *
     * @return The parameters of the function.
     */
    List<String> getParameters();
}
