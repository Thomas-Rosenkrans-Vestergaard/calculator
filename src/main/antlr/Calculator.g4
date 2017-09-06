grammar Calculator;

@header {
    package tvestergaard.calculator.antlr;
}

expression
    :   variableDeclaration
    |   functionDeclaration
    |   multiplicativeExpression
    |   COMMAND
    ;

variableDeclaration
    :   IDENTIFIER
        EQUALS
        multiplicativeExpression
    ;

functionDeclaration
    :   FUNC
        functionSignature
        EQUALS
        multiplicativeExpression
    ;

functionSignature
    :   IDENTIFIER
        functionParameters?
    ;

functionParameters
    :   PAREN_OPEN
        (IDENTIFIER (COMMA IDENTIFIER)*)?
        PAREN_CLOSE
    ;

multiplicativeExpression
    :   additiveExpression
    |   multiplicativeExpression multiplicativeOperator additiveExpression
    ;

multiplicativeOperator
    :   MUL_OP
    |   DIV_OP
    ;

additiveExpression
    :   primaryExpression
    |   additiveExpression additiveOperator primaryExpression
    ;

additiveOperator
    :   ADD_OP
    |   SUB_OP
    ;

primaryExpression
    :   parenthesizedExpression
    |   functionExpression
    |   VALUE_INT
    |   VALUE_FLOAT
    |   IDENTIFIER
    ;

parenthesizedExpression
    :   PAREN_OPEN multiplicativeExpression PAREN_CLOSE
    ;

functionExpression
    :   IDENTIFIER
        functionArguments
    ;

functionArguments
    :   PAREN_OPEN
        (multiplicativeExpression (COMMA multiplicativeExpression)*)?
        PAREN_CLOSE
    ;

COMMAND
    :   'functions'
    |   'memory'
    |   'reset'
    ;

FUNC
    :   'func'
    ;

EQUALS
    :   '='
    ;

ADD_OP
    :   '+'
    ;

SUB_OP
    :   '-'
    ;

MUL_OP
    :   '*'
    ;

DIV_OP
    :   '/'
    ;

PAREN_OPEN
    :   '('
    ;

PAREN_CLOSE
    :   ')'
    ;

VALUE_INT
    :   SUB_OP? [0-9]+
    ;

VALUE_FLOAT
    :   SUB_OP ? [0-9]* DOT [0-9]+
    ;

DOT
    :   '.'
    ;

COMMA
    :   ','
    ;

IDENTIFIER
    :   [a-zA-Z_]+
    ;

WHITESPACE
    :   [ \t\r\n]* -> skip
    ;