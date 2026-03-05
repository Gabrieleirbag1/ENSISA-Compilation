package fr.uha.hassenforder.cage.xon.model.xon;

public enum NodeType {
	VOID,

	JSON,
	OBJECT,
	ARRAY,
	PAIR,
	LIST,

	CONSTANT,
	VARIABLE,

	INSTR_LET,

	EXPR_ADD,
	EXPR_SUB,
	EXPR_MULTIPLY,
	EXPR_DIVIDE,
	EXPR_MODULO,
	EXPR_TERNARY,
	
	INSTR_IF,
	INSTR_LOOP,
	
}
