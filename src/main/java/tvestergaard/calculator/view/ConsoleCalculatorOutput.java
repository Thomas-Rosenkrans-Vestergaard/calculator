package tvestergaard.calculator.view;

import tvestergaard.calculator.logic.Calculator;
import tvestergaard.calculator.logic.CalculatorOutput;
import tvestergaard.calculator.logic.Function;
import tvestergaard.calculator.logic.ProgramException;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCalculatorOutput implements CalculatorOutput
{

    public static void main(String[] args) throws ProgramException
    {
        ConsoleCalculatorOutput consoleCalculatorOutput = new ConsoleCalculatorOutput();
        Calculator              calculator              = new Calculator(consoleCalculatorOutput);
        Scanner                 scanner                 = new Scanner(System.in);

        System.out.println("Ready.");

        while (true) {
            String input = scanner.nextLine();
            calculator.execute(input + '\n');
        }
    }

    /**
     * Print an error to output.
     *
     * @param error The contents to output.
     */
    @Override public void error(String error)
    {
        System.out.println(
                String.format(
                        "%s %s %s",
                        Colors.ANSI_RED,
                        error,
                        Colors.ANSI_RESET
                )
        );
    }

    @Override public void result(Double value)
    {
        System.out.println(
                String.format(
                        "%s %s %s",
                        Colors.ANSI_GREEN,
                        value,
                        Colors.ANSI_GREEN
                )
        );
    }

    @Override public void printFunctions(Collection<Function> functions)
    {
        System.out.print(Colors.ANSI_CYAN);
        System.out.println("Functions:");
        for (Function function : functions) {
            System.out.println(String.format("    %s", function.getSignature()));
        }

        System.out.print(Colors.ANSI_RESET);
    }

    @Override public void printVariables(Map<String, Double> variables)
    {
        System.out.print(Colors.ANSI_CYAN);
        System.out.println("Variables:");
        for (Map.Entry<String, Double> variable : variables.entrySet()) {
            System.out.println(String.format("    %s = %f", variable.getKey(), variable.getValue()));
        }

        System.out.print(Colors.ANSI_RESET);
    }

    @Override public void printConstants(Map<String, Double> constants)
    {

        System.out.print(Colors.ANSI_CYAN);
        System.out.println("Constants:");
        for (Map.Entry<String, Double> constant : constants.entrySet()) {
            System.out.println(String.format("    %s = %f", constant.getKey(), constant.getValue()));
        }

        System.out.print(Colors.ANSI_RESET);
    }

    /**
     * Called when the 'print' command is called. This handle is called once for every argument provided to the command.
     * If one of the arguments provided to the command causes an error, the error() method is instead called.
     *
     * @param value The value of the argument provided to the command.
     */
    @Override public void printValues(Double value)
    {
        System.out.println(
                String.format(
                        "%s %s %s",
                        Colors.ANSI_GREEN,
                        value,
                        Colors.ANSI_GREEN
                )
        );
    }
}
