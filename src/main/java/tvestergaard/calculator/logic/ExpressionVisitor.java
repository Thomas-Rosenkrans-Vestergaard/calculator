package tvestergaard.calculator.logic;

import tvestergaard.calculator.antlr.CalculatorBaseVisitor;
import tvestergaard.calculator.antlr.CalculatorParser.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionVisitor extends CalculatorBaseVisitor<ExpressionResult>
{

    /**
     * The memory of the calculator.
     */
    protected final CalculatorMemory   memory;
    protected final FunctionCollection functions;

    public ExpressionVisitor(CalculatorMemory memory, FunctionCollection functions)
    {
        this.memory = memory;
        this.functions = functions;
    }

    @Override public ExpressionResult visitExpression(ExpressionContext ctx)
    {
        MultiplicativeExpressionContext multiplicativeExpressionContext = ctx.multiplicativeExpression();
        if (multiplicativeExpressionContext != null) {
            return visitMultiplicativeExpression(multiplicativeExpressionContext);
        }

        return ExpressionResult.error("Error when visiting expression.");
    }

    @Override public ExpressionResult visitMultiplicativeExpression(MultiplicativeExpressionContext ctx)
    {
        MultiplicativeOperatorContext operatorContext = ctx.multiplicativeOperator();
        if (operatorContext != null) {

            String operator = operatorContext.getText();

            if (operator.equals("*")) {

                ExpressionResult left  = visitMultiplicativeExpression(ctx.multiplicativeExpression());
                ExpressionResult right = visitAdditiveExpression(ctx.additiveExpression());

                if (left.isError())
                    return left;

                if (right.isError())
                    return right;

                return ExpressionResult.success(left.getValue() * right.getValue());
            }

            if (operator.equals("/")) {
                ExpressionResult left  = visitMultiplicativeExpression(ctx.multiplicativeExpression());
                ExpressionResult right = visitAdditiveExpression(ctx.additiveExpression());

                if (left.isError())
                    return left;

                if (right.isError())
                    return right;

                if (right.getValue() == 0) {
                    throw new RuntimeException("You cannot divide by 0.");
                }

                return ExpressionResult.success(left.getValue() / right.getValue());
            }
        }

        AdditiveExpressionContext additiveExpressionContext = ctx.additiveExpression();
        if (additiveExpressionContext != null) {
            return visitAdditiveExpression(additiveExpressionContext);
        }

        return ExpressionResult.error("Error when visiting multiplicativeExpression.");
    }

    @Override public ExpressionResult visitAdditiveExpression(AdditiveExpressionContext ctx)
    {

        AdditiveOperatorContext operatorContext = ctx.additiveOperator();
        if (operatorContext != null) {

            String operator = operatorContext.getText();
            if (operator.equals("+")) {
                ExpressionResult left  = visitAdditiveExpression(ctx.additiveExpression());
                ExpressionResult right = visitPrimaryExpression(ctx.primaryExpression());

                if (left.isError())
                    return left;

                if (right.isError())
                    return right;

                return ExpressionResult.success(left.getValue() + right.getValue());
            }

            if (operator.equals("-")) {
                ExpressionResult left  = visitAdditiveExpression(ctx.additiveExpression());
                ExpressionResult right = visitPrimaryExpression(ctx.primaryExpression());

                if (left.isError())
                    return left;

                if (right.isError())
                    return right;

                return ExpressionResult.success(left.getValue() - right.getValue());
            }
        }

        PrimaryExpressionContext primaryExpressionContext = ctx.primaryExpression();
        return visitPrimaryExpression(primaryExpressionContext);
    }

    @Override public ExpressionResult visitPrimaryExpression(PrimaryExpressionContext ctx)
    {
        ParenthesizedExpressionContext parenthesizedExpressionContext = ctx.parenthesizedExpression();
        if (parenthesizedExpressionContext != null) {
            return visitMultiplicativeExpression(parenthesizedExpressionContext.multiplicativeExpression());
        }

        FunctionExpressionContext functionExpressionContext = ctx.functionExpression();
        if (functionExpressionContext != null) {
            return visitFunctionExpression(functionExpressionContext);
        }

        VariableExpressionContext variableExpressionContext = ctx.variableExpression();
        if (variableExpressionContext != null) {
            return getVariableValue(ctx.getText());
        }

        LiteralExpressionContext literalExpressionContext = ctx.literalExpression();
        return ExpressionResult.success(Double.parseDouble(ctx.getText()));
    }

    @Override public ExpressionResult visitFunctionExpression(FunctionExpressionContext ctx)
    {
        String functionName = ctx.IDENTIFIER().getText();

        List<Double>             arguments        = new ArrayList<>();
        FunctionArgumentsContext argumentsContext = ctx.functionArguments();
        for (ExpressionContext expressionContext : argumentsContext.expression()) {
            ExpressionResult result = visitExpression(expressionContext);
            if (result.isError())
                return result;

            arguments.add(result.getValue());
        }

        Function function = functions.get(new ConcreteFunctionIdentifier(functionName, arguments.size()));

        if (function != null) {
            return function.call(this, memory, arguments);
        }

        return ExpressionResult.error(String.format("No such function '%s'.", functionName));
    }

    private ExpressionResult getVariableValue(String name)
    {
        if (!this.memory.hasValue(name)) {
            return ExpressionResult.error(String.format("No variable with name '%s'.", name));
        }

        return ExpressionResult.success(this.memory.getValue(name));
    }
}
