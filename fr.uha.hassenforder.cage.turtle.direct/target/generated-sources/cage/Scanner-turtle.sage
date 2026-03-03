
%%

%package fr.uha.hassenforder.cage.turtle.direct.reader.ttl
%class Scanner
%public 
%page 

%mode STD

%{
	private ISymbol fallback () {
		throw new Error("Unrecognized character '"+yytext()+"' -- ignored");
	}

%}
%{
  private class Region {
  	int state;
  	StringBuilder tmp;
  	int fromLine;
  	int fromColumn;

public Region(int state) {
	super();
	this.state = state;
	this.tmp = new StringBuilder();
	this.fromLine = yyline;
	this.fromColumn = yycolumn;
}
  }

 private java.util.Stack<Region> regions = new java.util.Stack<>();

 private void startRegion (int state) {
    regions.push(new Region (state));
    yybegin (state);
 }

 @SuppressWarnings("unused")
 private void appendRegion (String content) {
    if (! regions.empty()) {
       Region region = regions.peek();
       region.tmp.append(content);
    }
 }

 private Region endRegion () {
    Region region = null;
    if (! regions.empty()) {
       region = regions.pop();
    }
 int state = YYINITIAL;
    if (! regions.empty()) {
       state = regions.peek().state;
    }
    yybegin (state);
    return region;
 }

 @SuppressWarnings("unused")
 private ISymbol symbolRegion (Region region, ETerminal token) {
   if (region == null) {
     ILocation position = symbolFactory.newLocation (yyline+1, yycolumn+yylength());
     return symbolFactory.newSymbol(token, position, position, "");
   } else {
     String content = region.tmp.toString();
     ILocation left = symbolFactory.newLocation (region.fromLine+1, region.fromColumn+1);
     ILocation right = symbolFactory.newLocation (yyline+1, yycolumn+yylength());
     return symbolFactory.newSymbol(token, left, right, content);
   }
 }

%}

__REGEXP_1__ = ";"
__REGEXP_2__ = "avancer"
__REGEXP_3__ = "tourner"
__REGEXP_4__ = "sauter"
__REGEXP_5__ = "lever"
__REGEXP_6__ = "baisser"
__REGEXP_7__ = [0-9]+
__REGEXP_8__ = [ \t\f]
__REGEXP_9__ = \r\n|\r|\n
__REGEXP_10__ = "//".*
__REGEXP_11__ = "/*"
__REGEXP_12__ = "*/"
__REGEXP_13__ = [^]

%state COMMENT$State

%%

<YYINITIAL> {
  {__REGEXP_1__}		{ return symbol(ETerminal.__REGEXP_1__); }
  {__REGEXP_8__}		{  }
  {__REGEXP_9__}		{  }
  {__REGEXP_11__}		{ startRegion (COMMENT$State); }
  {__REGEXP_5__}		{ return symbol(ETerminal.__REGEXP_5__); }
  {__REGEXP_4__}		{ return symbol(ETerminal.__REGEXP_4__); }
  {__REGEXP_2__}		{ return symbol(ETerminal.__REGEXP_2__); }
  {__REGEXP_3__}		{ return symbol(ETerminal.__REGEXP_3__); }
  {__REGEXP_6__}		{ return symbol(ETerminal.__REGEXP_6__); }
  {__REGEXP_10__}		{  }
  {__REGEXP_7__}		{ return symbol(ETerminal.INTEGER, Integer.parseInt(yytext())); }
}

<COMMENT$State> {
  {__REGEXP_12__}		{ endRegion (); }
  {__REGEXP_13__}		{ }
}


[^]			 { fallback(); }

