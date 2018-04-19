package tvestergaard.calculator.logic;

import tvestergaard.calculator.antlr.CalculatorBaseListener;
import tvestergaard.calculator.antlr.CalculatorParser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatementListener extends CalculatorBaseListener
{

    /**
     * The interface use to output.
     */
    protected final CalculatorOutput output;

    /**
     * The memory being manipulated during the execution of the code.
     */
    protected final CalculatorMemory memory;

    /**
     * The functions that can be called during the execution of the code.
     */
    protected final FunctionCollection functions;

    /**
     * The visitor that executed expressions.
     */
    protected final ExpressionVisitor visitor;

    /**
     * Creates a new {@link StatementListener}.
     *
     * @param output
     * @param memory
     * @param functions
     */
    public StatementListener(CalculatorOutput output, CalculatorMemory memory, FunctionCollection functions)
    {
        this.output = output;
        this.memory = memory;
        this.functions = functions;
        this.visitor = new ExpressionVisitor(memory, functions);
    }

    @Override public void enterProgram(ProgramContext ctx)
    {
        for (StatementContext statementContext : ctx.statement()) {
            enterStatement(statementContext);
        }
    }

    @Override public void enterStatement(StatementContext ctx)
    {
        CommandContext commandContext = ctx.command();
        if (commandContext != null) {
            enterCommand(commandContext);
            return;
        }

        FunctionDeclarationContext functionDeclarationContext = ctx.functionDeclaration();
        if (functionDeclarationContext != null) {
            enterFunctionDeclaration(functionDeclarationContext);
            return;
        }

        VariableDeclarationContext variableDeclarationContext = ctx.variableDeclaration();
        if (variableDeclarationContext != null) {
            enterVariableDeclaration(variableDeclarationContext);
            return;
        }

        ConstantDeclarationContext constantDeclarationContext = ctx.constantDeclaration();
        if (constantDeclarationContext != null) {
            enterConstantDeclaration(constantDeclarationContext);
            return;
        }

        ExpressionContext expressionContext = ctx.expression();
        if (expressionContext != null) {
            ExpressionResult result = visitor.visitExpression(expressionContext);
            if (result.isError()) {
                output.error(result.getErrorReason());
                return;
            }

            output.result(result.getValue());
            return;
        }

        output.error("Unknown statement.");
    }

    @Override public void enterCommand(CommandContext ctx)
    {
        String commandName = ctx.IDENTIFIER().getText();

        if (commandName.equals("variables")) {
            output.printVariables(memory.getVariables());
            return;
        }

        if (commandName.equals("functions")) {
            output.printFunctions(functions.getFunctions());
            return;
        }

        if (commandName.equals("constants")) {
            output.printConstants(memory.getConstants());
            return;
        }

        if (commandName.equals("print")) {
            executePrintCommand(ctx);
            return;
        }

        output.error(String.format("Command '%s' is not defined.", commandName));
    }

    private void executePrintCommand(CommandContext ctx)
    {
        FunctionArgumentsContext functionArgumentsContext = ctx.functionArguments();
        if (functionArgumentsContext == null) {
            output.error("No arguments provided to print function.");
            return;
        }

        for (ExpressionContext expressionContext : functionArgumentsContext.expression()) {
            ExpressionResult result = visitor.visitExpression(expressionContext);
            if (result.isError()) {
                output.error(result.getErrorReason());
                return;
            }

            output.printValues(result.getValue());
        }
    }

    @Override public void enterFunctionDeclaration(FunctionDeclarationContext ctx)
    {
        SignatureContext          signatureContext  = ctx.signature();
        FunctionParametersContext parametersContext = signatureContext.functionParameters();
        ExpressionContext         expressionContext = ctx.expression();
        String                    functionName      = signatureContext.IDENTIFIER().getText();
        List<String> parameters = parametersContext.IDENTIFIER().stream()
                .map(parameter -> parameter.getText())
                .collect(Collectors.toList());

        validateFunctionParameters(parameters, signatureContext.getText());
        Function function = functions.get(ConcreteFunctionIdentifier.of(functionName, parameters.size()));

        if (function == null) {
            this.functions.add(new ConcreteCustomFunction(functionName, parameters, signatureContext.getText(), expressionContext));
            return;
        }

        output.error(String.format("Function '%s' already exists.", signatureContext.getText()));
    }

    @Override public void enterVariableDeclaration(VariableDeclarationContext ctx)
    {
        String variableName = ctx.IDENTIFIER().getText();

        ExpressionResult result = visitor.visitExpression(ctx.expression());

        if (result.isError()) {
            output.error(result.getErrorReason());
            output.error("The variable was not declared.");
            return;
        }

        this.memory.addVariable(variableName, result.getValue());
    }

    public void enterConstantDeclaration(ConstantDeclarationContext ctx)
    {
        String constantName = ctx.IDENTIFIER().getText();

        ExpressionResult result = visitor.visitExpression(ctx.expression());

        if (result.isError()) {
            output.error(result.getErrorReason());
            output.error("The constant was not declared.");
            return;
        }

        this.memory.addConstant(constantName, result.getValue());
    }

    @Override public void enterExpression(ExpressionContext ctx)
    {

        MultiplicativeExpressionContext multiplicativeContext = ctx.multiplicativeExpression();
        if (multiplicativeContext != null) {
            ExpressionResult result = visitor.visitMultiplicativeExpression(multiplicativeContext);
            if (result.isError()) {
                output.error("An error occurred while executing the expression.");
                return;
            }

            output.result(result.getValue());
        }
    }

    /**
     * Validates that no duplicate parameters exists in a list of strings.
     *
     * @param parameters The list of parameters to validate.
     * @param signature  The signature of the function to check.
     */
    private void validateFunctionParameters(List<String> parameters, String signature)
    {
        List<String> temp = new ArrayList<>();
        parameters.forEach(parameter -> {
            if (temp.contains(parameter)) {
                throw new RuntimeException(String.format("Duplicate parameter '%s' in function '%s'.", parameter, signature));
            }
            temp.add(parameter);
        });
    }
}
