grammar Calculator;

@header {
    package tvestergaard.calculator.antlr;
}

program
    :   (statement NL)+
        EOF
    ;

statement
    :   command
    |   functionDeclaration
    |   variableDeclaration
    |   constantDeclaration
    |   expression
    ;

command
    :   '!' IDENTIFIER functionArguments?
    ;


functionDeclaration
    :   FUNC signature EQUALS expression
    ;


signature
    :   IDENTIFIER functionParameters?
    ;

functionParameters
    :   PAREN_OPEN
        (IDENTIFIER (COMMA IDENTIFIER)*)?
        PAREN_CLOSE
    ;


variableDeclaration
    :   IDENTIFIER EQUALS expression
    ;

constantDeclaration
    :   CONST IDENTIFIER EQUALS expression
    ;

expression
    :   multiplicativeExpression
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
    |   variableExpression
    |   literalExpression
    ;

parenthesizedExpression
    :   PAREN_OPEN multiplicativeExpression PAREN_CLOSE
    ;

functionExpression
    :   IDENTIFIER functionArguments
    ;

functionArguments
    :   PAREN_OPEN
        (expression (COMMA expression)*)?
        PAREN_CLOSE
    ;

variableExpression
    :   IDENTIFIER
    ;

literalExpression
    :   VALUE_FLOAT
    |   VALUE_INT
    ;

CONST
    :   'const'
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

NL
    :   '\n'
    ;

IDENTIFIER
    :   [a-zA-Z_]+
    ;

WHITESPACE
    :   [ \t\r\n]+ -> skip
    ;