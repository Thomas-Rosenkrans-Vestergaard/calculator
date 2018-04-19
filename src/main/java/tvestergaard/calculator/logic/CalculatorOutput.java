package tvestergaard.calculator.logic;

import java.util.Collection;
import java.util.Map;

public interface CalculatorOutput
{

    /**
     * Called when an error occurs during the executing of the program.
     *
     * @param error The error message.
     */
    void error(String error);

    /**
     * Called when a result has been calculated.
     *
     * @param value The value.
     */
    void result(Double value);

    /**
     * Called when the 'functions' command is called.
     *
     * @param functions The functions defined in the calculator.
     */
    void printFunctions(Collection<Function> functions);

    /**
     * Called when the 'variables' command is called.
     *
     * @param variables The defined variables on the current frame of the calculator.
     */
    void printVariables(Map<String, Double> variables);


    /**
     * Called when the 'constants' command is called.
     *
     * @param constants The defined constants on the current frame of the calculator.
     */
    void printConstants(Map<String, Double> constants);

    /**
     * Called when the 'print' command is called. This handle is called once for every argument provided to the command.
     * If one of the arguments provided to the command causes an error, the error() method is instead called.
     *
     * @param value The value of the argument provided to the command.
     */
    void printValues(Double value);
}
