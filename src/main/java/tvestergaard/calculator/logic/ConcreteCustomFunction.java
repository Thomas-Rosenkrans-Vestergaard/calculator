package tvestergaard.calculator.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static tvestergaard.calculator.antlr.CalculatorParser.ExpressionContext;

public class ConcreteCustomFunction extends AbstractFunction implements CustomFunction
{

    /**
     * The parameters that the function accepts.
     */
    private List<String> parameters;

    /**
     * The signature of the function.
     */
    private String signature;

    /**
     * The expression that is executed when the function is called.
     */
    private ExpressionContext expressionContext;

    /**
     * Creates a new custom function.
     *
     * @param name              The name of the function.
     * @param parameters        The parameters of the function.
     * @param signature         The signature of the function.
     * @param expressionContext The expression that is executed when the function is called.
     */
    public ConcreteCustomFunction(String name, List<String> parameters, String signature, ExpressionContext expressionContext)
    {
        super(name, parameters.size());

        assert parameters != null;
        assert signature != null;
        assert expressionContext != null;

        this.parameters = parameters;
        this.signature = signature;
        this.expressionContext = expressionContext;
    }

    /**
     * Returns the parameters of the function.
     *
     * @return The parameters of the function.
     */
    @Override public List<String> getParameters()
    {
        return parameters;
    }

    /**
     * Calls the function.
     *
     * @param visitor   The visitor that is used when executing the function.
     * @param memory    The memory of the calculator.
     * @param arguments The arguments to pass to the function.
     * @return The result of the function call.
     */
    @Override public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
    {
        if (arguments.size() != this.getParameterCount()) {
            throw new RuntimeException(String.format("Function '%s' takes %d arguments, %d provided.", getSignature(), this.getParameterCount(), arguments.size()));
        }

        Map<String, Double> namedArguments = new HashMap<>();
        Iterator<String>    i1             = this.parameters.iterator();
        Iterator<Double>    i2             = arguments.iterator();
        while (i1.hasNext() && i2.hasNext())
            namedArguments.put(i1.next(), i2.next());

        memory.enterScope(namedArguments);
        ExpressionResult result = visitor.visitExpression(expressionContext);
        memory.exitScope();
        return result;
    }

    /**
     * Returns the signature of the function.
     *
     * @return The signature of the function.
     */
    @Override public String getSignature()
    {
        return signature;
    }
}
