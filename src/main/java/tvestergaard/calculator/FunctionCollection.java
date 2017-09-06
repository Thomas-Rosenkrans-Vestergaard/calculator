package tvestergaard.calculator;

import java.util.HashMap;
import java.util.Iterator;

public class FunctionCollection
{

	/**
	 * The functions in the collection.
	 */
	private HashMap<Integer, ConcreteFunction> functions = new HashMap<>();

	public ConcreteFunction get(Function function)
	{
		int hashCode = function.hashCode();
		if (!this.functions.containsKey(hashCode)) {
			throw new RuntimeException(String.format("No function with signature '%s' exists.", function.getSignature()));
		}

		return this.functions.get(hashCode);
	}

	/**
	 * Checks if a function exists in the collection.
	 *
	 * @param signature The signature to check for.
	 * @return true or false.
	 */
	public boolean has(Function signature)
	{
		return functions.containsKey(signature.hashCode());
	}

	/**
	 * Adds a new function to the collection.
	 *
	 * @param function The function to add.
	 */
	public void add(ConcreteFunction function)
	{
		int hashCode = function.hashCode();
		if (functions.containsKey(hashCode)) {
			throw new RuntimeException(String.format("Function with signature '%s' already exists.", function.getSignature()));
		}

		functions.put(hashCode, function);
	}

	/**
	 * Returns an iterator of the functions in the collection.
	 *
	 * @return An iterator.
	 */
	public Iterator<ConcreteFunction> iterate()
	{
		return functions.values().iterator();
	}
}
