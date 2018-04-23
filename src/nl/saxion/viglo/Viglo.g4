grammar Viglo;

program: importStatement* (structBlock | classBlock);

importStatement: IMPORT IMPORT_LITERAL;

structBlock: STRUCT NAME (':' functionCall (',' functionCall)*)? paramList block;
classBlock: CLASS NAME ('<' generic=type '>')? classList? rootBlock;
classList: ':' NAME (',' NAME)*;

rootBlock: '{' rootStatement* '}';
block: '{' statement* '}';

rootStatement: declareOnlyStatement | declareFunction;

statement: declareStatement
    | assignStatement
    | returnStatement
    | declareFunction
    | ifStatement
    | forStatement
    | whileStatement
    | chainExp
    | echoStatement
    ;

exp: NULL                                   #NullExpression
    | boolLiteral                           #BoolExpression
    | numberLiteral                         #NumberExpression
    | stringLiteral                         #StringExpression
    | charLiteral                           #CharExpression
    | rangeLiteral                          #RangeExpression
    | '(' exp ')'                           #BracketExpression
    | functionStatement                     #FunctionExpression
    | chainExp                              #ChainExpression
    | left=exp math_operator right=exp      #MathExpression
    | left=exp eq_operator right=exp        #EqualityExpression
    | left=exp comp_operator right=exp      #CompareExpression
    | OP_NOT exp                            #NotExpression
    | left=exp logic_operator right=exp     #LogicExpression
    | arrayCreate                           #ArrayExpression
    | listCreate                            #ListExpression
    ;

chainExp: chainPart* chainTail;
chainPart: (variable | functionCall) '.';
chainTail: (variable | functionCall);

ifStatement:
    IF_MARK exp block
    elseifStatement*
    elseStatement?
    ;
elseifStatement: ( ':?' exp block );
elseStatement: ( ':' block);

forStatement: FOR NAME IN exp block;

declareFunction: STATIC? FUNC NAME functionStatement;
functionStatement: paramList ':' type '=>' block;
functionCall: NAME '(' (exp (',' exp)*)? ')';
paramList: '(' (paramItem (',' paramItem)*)? ')';
paramItem: NAME ':' type;


declareStatement: declareInferStatement | declareOnlyStatement;
declareInferStatement: varKey=(CONST | LET) NAME ('=' exp);
declareOnlyStatement: varKey=(CONST | LET) NAME (':' type);

assignStatement: variable '=' exp;
returnStatement: RETURN exp;
echoStatement: ECHO exp;
whileStatement: WHILE exp DO block;

arrayCreate: ARRAY '<' type '>' '(' (exp (',' exp)*)? ')';
listCreate: LIST '<' type '>' '(' (exp (',' exp)*)? ')';

charLiteral: CHAR_STRING;
stringLiteral: STRING_STRING;
numberLiteral: intLiteral | floatLiteral | longLiteral | doubleLiteral;
type: NAME | INT | FLOAT | LONG | DOUBLE | BOOL | NUMBER;

math_operator: PLUS | MINUS | MULTIPLY | DEVIDE;
eq_operator: OP_EQ | OP_NOT_EQ;
comp_operator: OP_LESS | OP_LESSEQ | OP_MORE | OP_MOREEQ;
logic_operator: OP_AND | OP_OR;

rangeLiteral: '[' INT_LITERAL '...' INT_LITERAL ']';
longLiteral: LONG_LITERAL;
intLiteral: INT_LITERAL;
floatLiteral: FLOAT_LITERAL;
doubleLiteral: DOUBLE_LITERAL;
boolLiteral: BOOL_LITERAL;
variable: NAME ;

WS: [\r\n\t ]+ -> skip;

COMMENT: ( '~' ~[\r\n]* '\r'? '\n'
         | '<~' .*? '~>'
         ) -> channel(HIDDEN);

INT_LITERAL: [1-9][0-9]* | '0';
FLOAT_LITERAL: [0-9]+('.'[0-9]+)?'f';
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
OP_NOT_EQ: '!=';
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
WHILE: 'while';
DO: 'do';
STATIC: 'static';

IF_MARK: '?';
FOR: 'for';
IN: 'in';

NAME: [a-zA-Z_$][a-zA-Z0-9_$]*;

CHAR_STRING: '\'' CHAR_CHAR '\'';
CHAR_CHAR: ~('\\') | '\\\\' | '\\\'';
STRING_STRING: '"' (STRING_CHAR*) '"';
STRING_CHAR: ~('\\'|'"') | '\\\\' | '\\"';

IMPORT_LITERAL: '\'' [a-zA-Z0-9._]+ '\'';

