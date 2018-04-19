package tvestergaard.calculator.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class FunctionCollection
{

    /**
     * The functions in the collection.
     */
    private HashMap<Integer, Function> functions = new HashMap<>();

    /**
     * Returns a function matching the provided identifier.
     *
     * @param identifier The identifier of the function to return.
     * @return The fucntion matching the provided identifier. Returns null if no such function exists.
     */
    public Function get(FunctionIdentifier identifier)
    {
        return functions.get(identifier.getIdentifier());
    }

    /**
     * Adds a new function to the collection.
     *
     * @param function The function to add to the collection.
     */
    public void add(Function function)
    {
        functions.put(function.getIdentifier(), function);
    }

    /**
     * Returns the functions defined in memory.
     *
     * @return The functions defined in memory.
     */
    public Collection<Function> getFunctions()
    {
        return Collections.unmodifiableCollection(functions.values());
    }
}
