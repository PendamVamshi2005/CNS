//calc.l
%{
#include "y.tab.h"
#include <stdlib.h>
%}
%%
[0-9]+  { yylval=atoi(yytext); return NUM; }
[ \t]   ;
\n      return 0;
.       return yytext[0];
%%
int yywrap(){ return 1; }

//calc.y
%{
#include<stdio.h>
%}
%token NUM
%%
E: E '+' E { $$=$1+$3; }
 | E '*' E { $$=$1*$3; }
 | NUM     { $$=$1; }
 ;
%%
int main(){
    printf("Enter expression: ");
    yyparse();
    return 0;
}
int yyerror(char *s){
    printf("Invalid expression");
    return 0;
}

///////
yacc -d calc.y
lex calc.l
gcc y.tab.c lex.yy.c -ll
./a.out

//ast.l
%{
#include "y.tab.h"
%}
%%
[a-zA-Z]+  return ID;
[ \t]      ;
\n         return 0;
.          return yytext[0];
%%
int yywrap(){ return 1; }

//ast.y
%{
#include<stdio.h>
#include<stdlib.h>

struct node {
    char *name;
    struct node *l,*r;
};

struct node* create(char *n,struct node *l,struct node *r){
    struct node *p=malloc(sizeof(struct node));
    p->name=n;
    p->l=l;
    p->r=r;
    return p;
}

void print(struct node *p){
    if(p){
        printf("%s ",p->name);
        print(p->l);
        print(p->r);
    }
}
struct node *root;
%}

%union { struct node *ptr; }
%token ID
%type <ptr> E T F

%%
S: E { root=$1; print(root); };

E: E '+' T { $$=create("+",$1,$3); }
 | T       { $$=$1; };

T: T '*' F { $$=create("*",$1,$3); }
 | F       { $$=$1; };

F: '(' E ')' { $$=$2; }
 | ID        { $$=create(yytext,NULL,NULL); };
%%

int main(){
    printf("Enter expression: ");
    yyparse();
    printf("\n");
    return 0;
}
int yyerror(char *s){
    printf("Invalid expression");
    return 0;
}

/////////
yacc -d ast.y
lex ast.l
gcc y.tab.c lex.yy.c -ll
./a.out

//count.l
%{
#include<stdio.h>
int v=0,c=0;
%}
%%
[aeiouAEIOU]  { v++; }
[a-zA-Z]      { c++; }
.|\n          ;
%%
int main(){
    printf("Enter string: ");
    yylex();
    printf("Vowels = %d\n",v);
    printf("Consonants = %d\n",c);
    return 0;
}
int yywrap(){ return 1; }

//////
lex count.l
gcc lex.yy.c -ll
./a.out
