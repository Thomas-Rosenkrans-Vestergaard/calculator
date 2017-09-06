package tvestergaard.calculator;

import java.util.List;

public interface Function
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
}
