grammar Viglo;

program: importStatement* (structBlock | classBlock);

importStatement: IMPORT IMPORT_LITERAL;



structBlock: STRUCT NAME (':' functionCall (',' functionCall)*)? paramList block;
classBlock: CLASS NAME classList? block;
classList: ':' NAME (',' NAME)*;

block: '{' statement* '}';

statement: declareStatement
    | assignStatement
    | returnStatement
    | declareFunction
    | ifStatement
    | forStatement
    | chainExp
    | echoStatement
    ;

exp: NULL                           #NullExpression
    | boolLiteral                   #BoolExpression
    | numberLiteral                 #NumberExpression
    | stringLiteral                 #StringExpression
    | charLiteral                   #CharExpression
    | rangeLiteral                  #RangeExpression
    | functionStatement             #FunctionExpression
    | chainExp                      #ChainExpression
    | exp math_operator exp         #MathExpression
    | exp eq_operator exp           #EqualityExpression
    | OP_NOT exp                    #NotExpression
    | exp logic_operator exp        #LogicExpression
    | arrayCreate                   #ArrayExpression
    | listCreate                    #ListExpression
    ;

chainExp: (variable | functionCall) ('.' (variable | functionCall))*;

ifStatement:
    IF_MARK exp block
    elseifStatement*
    elseStatement?
    ;
elseifStatement: ( ':?' exp block );
elseStatement: ( ':' block);

forStatement: FOR NAME IN exp block;

declareFunction: FUNC NAME functionStatement;
functionStatement: paramList ':' type '=>' block;
functionCall: NAME '(' (exp (',' exp)*)? ')';
paramList: '(' (NAME ':' type (',' NAME ':' type)*)? ')';

declareStatement: varKey=(CONST | LET) NAME (('=' exp) | (':' type));
assignStatement: variable '=' exp;
returnStatement: RETURN exp;
echoStatement: ECHO exp;

arrayCreate: ARRAY '<' type '>' '(' (exp (',' exp)*)? ')';
listCreate: LIST '<' type '>' '(' (exp (',' exp)*)? ')';

charLiteral: CHAR_STRING;
stringLiteral: STRING_STRING;
numberLiteral: intLiteral | floatLiteral | longLiteral | doubleLiteral;
type: NAME | INT | FLOAT | LONG | DOUBLE | BOOL | NUMBER;

math_operator: PLUS | MINUS | MULTIPLY | DEVIDE;
eq_operator: OP_LESS | OP_LESSEQ | OP_EQ | OP_MORE | OP_MOREEQ;
logic_operator: OP_AND | OP_OR;

rangeLiteral: '[' INT_LITERAL '...' INT_LITERAL ']';
longLiteral: LONG_LITERAL;
intLiteral: INT_LITERAL;
floatLiteral: FLOAT_LITERAL;
doubleLiteral: DOUBLE_LITERAL;
boolLiteral: BOOL_LITERAL;
variable: NAME ;

WS: [\r\n\t ]+ -> skip;

INT_LITERAL: [1-9][0-9]* | '0';
FLOAT_LITERAL: [0-9]+'.'[0-9]+'f';
DOUBLE_LITERAL: [0-9]+'.'[0-9]+;
LONG_LITERAL: [1-9][0-9]*'l' | '0l';
BOOL_LITERAL: 'true' | 'false';

INT: 'int';
LONG: 'long';
FLOAT: 'float';
DOUBLE: 'double';
BOOL: 'bool';
NUMBER: 'number';

CONST: 'const';
LET: 'let';
RETURN: 'return';
NULL: 'null';

PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DEVIDE: '/';

OP_LESS: '<';
OP_MORE: '>';
OP_EQ: '==';
OP_LESSEQ: '<=';
OP_MOREEQ: '>=';

OP_AND: 'and';
OP_OR: 'or';
OP_NOT: 'not';

ARRAY: 'array';
LIST: 'list';
ECHO: 'echo';

STRUCT: 'struct';
CLASS: 'class';
FUNC: 'func';
IMPORT: 'import';

IF_MARK: '?';
FOR: 'for';
IN: 'in';

NAME: [a-zA-Z_$][a-zA-Z0-9_$]*;

CHAR_STRING: '\'' CHAR_CHAR '\'';
CHAR_CHAR: ~('\\') | '\\\\' | '\\\'';
STRING_STRING: '"' (STRING_CHAR*) '"';
STRING_CHAR: ~('\\') | '\\\\' | '\\"';

IMPORT_LITERAL: '\'' [a-zA-Z0-9._]+ '\'';

COMMENT
    :   ' ~>' .*? '<~' -> channel(HIDDEN)
    ;

LINE_COMMENT
    :   '~' ~[\r\n]* -> channel(HIDDEN)
    ;

