package tvestergaard.calculator.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorMemory
{

    /**
     * The stacks of memory in the calculator.
     */
    private final Stack<Map<String, Double>> stack = new Stack<>();

    /**
     * The constants in the memory.
     */
    private final Map<String, Double> constants = new HashMap<>();

    /**
     * The number of scopes in the calculator.
     */
    private int scopeCount = 1;

    /**
     * Initializes the global scope.
     */
    public CalculatorMemory()
    {
        this.stack.add(new HashMap<>());
    }

    /**
     * Adds a new set of variables on the localVariables.
     *
     * @param variables The variables to addVariable.
     */
    public void enterScope(Map<String, Double> variables)
    {
        this.stack.push(variables);
        this.scopeCount++;
    }

    /**
     * Exits the current local scope.
     */
    public void exitScope()
    {
        if (this.stack.size() == 1) {
            throw new RuntimeException("Attempted to exit global scope.");
        }

        this.stack.pop();
        this.scopeCount--;
    }

    /**
     * Adds a new constant to memory.
     *
     * @param name  The name of the constant.
     * @param value The value of the constant.
     */
    public void addConstant(String name, Double value)
    {
        if (this.constants.containsKey(name)) {
            throw new RuntimeException(String.format("Constant '%s' already exists.", name));
        }

        this.constants.put(name, value);
    }

    /**
     * Adds a new variable to memory.
     *
     * @param name  The name of the new memory.
     * @param value The value of the new memory.
     */
    public void addVariable(String name, Double value)
    {
        if (this.constants.containsKey(name)) {
            throw new RuntimeException(String.format("A constant with the name '%s' already exists.", name));
        }

        this.stack.peek().put(name, value);
    }

    /**
     * Returns a piece of memory.
     *
     * @param name The name of the memory to retrieve.
     * @return The value of the memory.
     */
    public Double getValue(String name)
    {
        if (this.constants.containsKey(name)) {
            return this.constants.get(name);
        }

        Map<String, Double> scope = this.stack.peek();
        if (!scope.containsKey(name)) {
            throw new RuntimeException(String.format("No variable with name '%s'.", name));
        }

        return scope.get(name);
    }

    /**
     * Checks if a memory location with the given name exists.
     *
     * @param name The name of the memory location.
     * @return true or false.
     */
    public boolean hasValue(String name)
    {
        if (this.constants.containsKey(name)) {
            return true;
        }

        return this.stack.peek().containsKey(name);
    }

    /**
     * Returns a map containing the variables on the current stack frame in memory and their values.
     *
     * @return The map.
     */
    public Map<String, Double> getVariables()
    {
        return Collections.unmodifiableMap(stack.peek());
    }

    /**
     * Returns the defined constants and their value in memory.
     *
     * @return The defined constants.
     */
    public Map<String, Double> getConstants()
    {
        return Collections.unmodifiableMap(constants);
    }
}
