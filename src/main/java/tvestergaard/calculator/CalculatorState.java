package tvestergaard.calculator;

import java.util.List;

public class CalculatorState
{

	/**
	 * The memory of the state.
	 */
	public final CalculatorMemory memory = new CalculatorMemory();

	/**
	 * The custom functions defined in the state.
	 */
	public final FunctionCollection functions = new FunctionCollection();

	/**
	 * Initialized predefined constants and functions.
	 */
	public CalculatorState()
	{
		addConstants();
		addFunctions();
	}

	/**
	 * Adds the built in constants to the calculator.
	 */
	public void addConstants()
	{
		this.memory.addConstant("PI", Math.PI);
		this.memory.addConstant("E", Math.E);
	}

	/**
	 * Adds the built in functions to the calculator.
	 */
	public void addFunctions()
	{

		this.functions.add(new PredefinedConcreteFunction("abs", 1, "abs(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.abs(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("acos", 1, "acos(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.acos(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("asin", 1, "asin(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.asin(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("atan", 1, "atan(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.atan(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("cbrt", 1, "cbrt(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.cbrt(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("ceil", 1, "ceil(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.ceil(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("cos", 1, "cos(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.cos(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("cosh", 1, "cosh(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.cosh(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("exp", 1, "exp(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.exp(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("floor", 1, "floor(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.floor(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("log", 1, "log(number)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.log(arguments.get(0)) / Math.E;
			}
		});

		this.functions.add(new PredefinedConcreteFunction("log", 2, "log(number,base)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.log(arguments.get(0)) / Math.log(arguments.get(1));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("max", 2, "max(a,b)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.max(arguments.get(0), arguments.get(1));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("min", 2, "min(a,b)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.min(arguments.get(0), arguments.get(1));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("pow", 2, "pow(a,b)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.pow(arguments.get(0), arguments.get(1));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("random", 0, "random()")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.random();
			}
		});

		this.functions.add(new PredefinedConcreteFunction("rint", 1, "rint(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.rint(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("root", 2, "root(a,b)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.pow(Math.E, Math.log(arguments.get(0)) / arguments.get(1));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("round", 1, "round(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return (double) Math.round(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("signum", 1, "signum(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.signum(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("sin", 1, "sin(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.sin(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("sinh", 1, "sinh(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.sinh(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("sqrt", 1, "sqrt(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.sqrt(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("tan", 1, "tan(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.tan(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("tanh", 1, "tanh(a)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.tanh(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("toDegrees", 1, "toDegrees(radians)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.toDegrees(arguments.get(0));
			}
		});

		this.functions.add(new PredefinedConcreteFunction("toRadians", 1, "toRadians(degrees)")
		{
			@Override public Double call(CalculatorState state, List<Double> arguments)
			{
				return Math.toRadians(arguments.get(0));
			}
		});
	}
}