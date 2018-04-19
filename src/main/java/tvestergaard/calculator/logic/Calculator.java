package tvestergaard.calculator.logic;

import org.antlr.v4.runtime.*;
import tvestergaard.calculator.antlr.CalculatorLexer;
import tvestergaard.calculator.antlr.CalculatorParser;

import java.util.List;

public class Calculator
{

    /**
     * The memory of the calculator, contains the defined variables and constants.
     */
    private final CalculatorMemory   memory    = new CalculatorMemory();

    /**
     * The functions defined in the calculator.
     */
    private final FunctionCollection functions = new FunctionCollection();

    /**
     * The object used to output to the caller.
     */
    private final CalculatorOutput  output;

    /**
     * The listener used to walk the program statements when executing the provided program.
     */
    private final StatementListener listener;

    /**
     * The visitor used to walk the program expression when executing the provided program.
     */
    private final ExpressionVisitor visitor = new ExpressionVisitor(memory, functions);

    /**
     * Creates a new {@link Calculator}.
     *
     * @param output The object use for output.
     */
    public Calculator(CalculatorOutput output)
    {
        this.output = output;
        this.listener = new StatementListener(output, memory, functions);
        addConstants();
        addFunctions();
    }

    /**
     * Executes the provided code.
     *
     * @param program The program to execute.
     * @throws ProgramException When an exception occurs while executing the code.
     */
    public void execute(String program) throws ProgramException
    {
        try {
            CharStream        charStream = CharStreams.fromString(program);
            CalculatorLexer   lexer      = new CalculatorLexer(charStream);
            CommonTokenStream tokens     = new CommonTokenStream(lexer);
            CalculatorParser  parser     = new CalculatorParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener()
            {
                @Override public void syntaxError(
                        Recognizer<?, ?> recognizer,
                        Object offendingSymbol,
                        int line,
                        int charPositionInLine,
                        String msg,
                        RecognitionException e)
                {
                    output.error(msg);
                }
            });

            CalculatorParser.ProgramContext programContext = parser.program();
            listener.enterProgram(programContext);

        } catch (Exception e) {
            output.error(e.getMessage());
        }
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

        this.functions.add(new PredefinedFunction("abs", 1, "abs(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.abs(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("acos", 1, "acos(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.acos(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("asin", 1, "asin(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.asin(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("atan", 1, "atan(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.atan(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("cbrt", 1, "cbrt(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.cbrt(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("ceil", 1, "ceil(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.ceil(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("cos", 1, "cos(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.cos(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("cosh", 1, "cosh(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.cosh(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("exp", 1, "exp(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.exp(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("floor", 1, "floor(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.floor(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("log", 1, "log(number)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.log(arguments.get(0)) / Math.E);
            }
        });

        this.functions.add(new PredefinedFunction("log", 2, "log(number,base)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.log(arguments.get(0)) / Math.log(arguments.get(1)));
            }
        });

        this.functions.add(new PredefinedFunction("max", 2, "max(a,b)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.max(arguments.get(0), arguments.get(1)));
            }
        });

        this.functions.add(new PredefinedFunction("min", 2, "min(a,b)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.min(arguments.get(0), arguments.get(1)));
            }
        });

        this.functions.add(new PredefinedFunction("pow", 2, "pow(a,b)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.pow(arguments.get(0), arguments.get(1)));
            }
        });

        this.functions.add(new PredefinedFunction("random", 0, "random()")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.random());
            }
        });

        this.functions.add(new PredefinedFunction("rint", 1, "rint(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.rint(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("root", 2, "root(a,b)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.pow(Math.E, Math.log(arguments.get(0)) / arguments.get(1)));
            }
        });

        this.functions.add(new PredefinedFunction("round", 1, "round(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success((double) Math.round(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("round", 2, "round(a,decimals)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                long   roundedFactor = Math.round(arguments.get(1));
                double factor        = Math.pow((double) 10, (double) roundedFactor);
                return ExpressionResult.success((double) Math.round(arguments.get(0) * factor) / factor);
            }
        });

        this.functions.add(new PredefinedFunction("signum", 1, "signum(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.signum(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("sin", 1, "sin(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.sin(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("sinh", 1, "sinh(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.sinh(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("sqrt", 1, "sqrt(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.sqrt(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("tan", 1, "tan(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.tan(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("tanh", 1, "tanh(a)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.tanh(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("toDegrees", 1, "toDegrees(radians)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.toDegrees(arguments.get(0)));
            }
        });

        this.functions.add(new PredefinedFunction("toRadians", 1, "toRadians(degrees)")
        {
            @Override
            public ExpressionResult call(ExpressionVisitor visitor, CalculatorMemory memory, List<Double> arguments)
            {
                return ExpressionResult.success(Math.toRadians(arguments.get(0)));
            }
        });
    }
}
