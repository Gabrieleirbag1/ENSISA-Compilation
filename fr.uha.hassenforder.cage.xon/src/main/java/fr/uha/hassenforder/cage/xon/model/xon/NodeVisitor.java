package fr.uha.hassenforder.cage.xon.model.xon;

public abstract class NodeVisitor implements INodeVisitor {
	
	public void visit_Node (Node node) {
		switch (node.getType()) {
		case VOID :				break;
		case JSON :				visit_JsonNode(node); break;
		case OBJECT :			visit_ObjectNode(node); break;
		case ARRAY :			visit_ArrayNode(node); break;
		case PAIR :				visit_PairNode(node); break;
		case LIST :				visit_ListNode(node); break;
		case CONSTANT :			visit_ConstantNode(node); break;
		case VARIABLE :			visit_VariableNode(node); break;
		case INSTR_LET :		visit_LetNode(node); break;
		case EXPR_ADD :			visit_Add(node); break;
		case EXPR_SUB :			visit_Sub(node); break;
		case EXPR_MULTIPLY :	visit_Multiply(node); break;
		case EXPR_DIVIDE :		visit_Divide(node); break;		
		case EXPR_MODULO :		visit_Modulo(node); break;		
		case EXPR_TERNARY :		visit_Ternary(node); break;		
		case INSTR_IF :			visit_If(node); break;
		case INSTR_LOOP :		visit_Loop(node); break;
		}
	}

	public abstract void visit_JsonNode(Node node);

	public abstract void visit_ObjectNode(Node node);
	public abstract void visit_ArrayNode(Node node);
	public abstract void visit_PairNode(Node node);
	public abstract void visit_ListNode(Node node);

	public abstract void visit_ConstantNode(Node node);	
	public abstract void visit_VariableNode(Node node);

	public abstract void visit_LetNode(Node node);

	public abstract void visit_Divide(Node node);
	public abstract void visit_Multiply(Node node);
	public abstract void visit_Add(Node node);
	public abstract void visit_Sub(Node node);
	public abstract void visit_Modulo(Node node);
	public abstract void visit_Ternary(Node node);

	public abstract void visit_Loop(Node node);
	public abstract void visit_If(Node node);

}
