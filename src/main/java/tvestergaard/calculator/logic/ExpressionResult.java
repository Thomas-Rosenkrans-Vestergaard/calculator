package tvestergaard.calculator.logic;

/**
 * Represents a result of some computation made by the {@link ExpressionVisitor}. The object contains an error message
 * when an error has occurred, or contains a value when the computation was successful.
 */
public class ExpressionResult
{

    /**
     * The error message, in case the computation caused an error.
     */
    private final String error;

    /**
     * The value, in case the computation was successful.
     */
    private final Double value;

    /**
     * Creates a new {@link}
     *
     * @param error The error message, in case the computation caused an error.
     * @param value The value, in case the computation was successful.
     */
    private ExpressionResult(String error, Double value)
    {
        this.error = error;
        this.value = value;
    }

    /**
     * Creates a new {@link ExpressionResult} option representing an error state.
     *
     * @param error The reason why the error occurred.
     * @return The newly created instant of {@link ExpressionResult}.
     */
    public static ExpressionResult error(String error)
    {
        return new ExpressionResult(error, null);
    }


    /**
     * Creates a new {@link ExpressionResult} option representing a succes state.
     *
     * @param value The result of the successful computation.
     * @return The newly created instant of {@link ExpressionResult}.
     */
    public static ExpressionResult success(Double value)
    {
        return new ExpressionResult(null, value);
    }

    /**
     * Returns true if the option is in an error state (The object has an error message).
     *
     * @return true if the option is in an error state (The object has an error message).
     */
    public boolean isError()
    {
        return this.error != null;
    }

    /**
     * Returns the error message. Returns null if the result option is in a success state.
     *
     * @return The error message. Returns null if the result option is in a success state.
     */
    public String getErrorReason()
    {
        return this.error;
    }

    /**
     * Returns the result value. Returns null if the result option is an an error state.
     *
     * @return The result value. Returns null if the result option is an an error state.
     */
    public Double getValue()
    {
        return this.value;
    }
}
