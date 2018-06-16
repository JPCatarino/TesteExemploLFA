grammar examGrmr;

main: statList EOF;

statList: (stat)*;

stat: print
      |assign
      ;

print: 'print' printV;

printV: string      #printS
        |ID         #printID
        ;

assign: ID ':' assignV;

assignV: input      #assignIn
         |string    #assignS
         |op       #assignOP
         ;

op: l=op '+' r=op                              #opCon
    |'('tbc=op'/'stc=string'/'sfc=string')'    #opChange
    |printV                                    #opValue
    ;


input: 'input' '(' string ')';

string: STRING;

ID: [A-Za-z0-9]+;
STRING: '"'~('\r' | '\n' | '"')*'"';
WS:[ \t\r\n] -> skip;
COMMENT: '//' ~[\r\n]* -> skip;