package tvestergaard.calculator;

import java.util.*;

public class CalculatorMemory
{

	/**
	 * The global memory in the calculator.
	 */
	private final Stack<Map<String, Double>> stack = new Stack<>();

	/**
	 * The number of scopes in the calculator.
	 */
	private int scopeCount = 1;

	/**
	 * Initializes the global scope.
	 */
	public CalculatorMemory()
	{
		reset();
	}

	/**
	 * Resets the memory.
	 */
	public void reset()
	{
		this.stack.clear();
		this.stack.add(new HashMap<>());
	}

	/**
	 * Adds a new set of variables on the localVariables.
	 *
	 * @param variables The variables to add.
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
	 * Adds a piece of memory to the global memory.
	 *
	 * @param name  The name of the new memory.
	 * @param value The value of the new memory.
	 */
	public void add(String name, Double value)
	{
		this.stack.peek().put(name, value);
	}

	/**
	 * Returns a piece of memory.
	 *
	 * @param name The name of the memory to retrieve.
	 * @return The value of the memory.
	 */
	public Double get(String name)
	{
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
	public boolean has(String name)
	{
		return this.stack.peek().containsKey(name);
	}

	/**
	 * Iterates over the values in the current scope.
	 *
	 * @return An iterator.
	 */
	public Iterator<Map.Entry<String, Double>> iterate()
	{
		return this.stack.peek().entrySet().iterator();
	}
}
