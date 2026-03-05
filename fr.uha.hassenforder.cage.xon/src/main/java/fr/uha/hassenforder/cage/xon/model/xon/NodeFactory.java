package fr.uha.hassenforder.cage.xon.model.xon;

import java.util.List;

public class NodeFactory {

    public NodeFactory() {
    }

    public Node createJsonNode(Node child) {
        Node node = new Node();
        node.setType(NodeType.JSON);
		node.addNode(child);
        return node;
    }

    public Node createObjectNode(List<Node> children) {
        Node node = new Node();
        node.setType(NodeType.OBJECT);
		node.addNodes(children);
        return node;
    }

    public Node createArrayNode(List<Node> children) {
        Node node = new Node();
        node.setType(NodeType.ARRAY);
		node.addNodes(children);
        return node;
    }

    private Node createConstantNode(NodeValue value) {
        Node node = new Node();
        node.setType(NodeType.CONSTANT);
        node.setValue(value);
        return node;
    }

    private Node createVariableNode(NodeValue value) {
        Node node = new Node();
        node.setType(NodeType.VARIABLE);
        node.setValue(value);
        return node;
    }

    // Json pair: "name" : value
    public Node createPairNode(String name, Node value) {
        Node node = new Node();
        node.setType(NodeType.PAIR);
        NodeValue nodeValue = new NodeValue(name);
        node.addNode(createConstantNode(nodeValue));
        node.addNode(value);
        return node;
    }

    // Xon pair: variable : value
    public Node createPairNode(Node name, Node value) {
        Node node = new Node();
        node.setType(NodeType.PAIR);
        node.addNode(name);
        node.addNode(value);
        return node;
    }

    public Node createLetNode(String name, Node value) {
        Node node = new Node();
        node.setType(NodeType.INSTR_LET);
        NodeValue nodeValue = new NodeValue(name);
        node.addNode(createConstantNode(nodeValue));
        node.addNode(value);
        return node;
    }

    public Node createGetNode(String name) {
        NodeValue nodeValue = new NodeValue(name);
        return createVariableNode(nodeValue);
    }

    public Node createStringNode(String text) {
        NodeValue nodeValue = new NodeValue(text);
        return createConstantNode(nodeValue);
    }

    public Node createIntegerNode(int value) {
        NodeValue nodeValue = new NodeValue(value);
        return createConstantNode(nodeValue);
    }

    public Node createRealNode(double value) {
        NodeValue nodeValue = new NodeValue(value);
        return createConstantNode(nodeValue);
    }

    public Node createAddNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_ADD);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createSubNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_SUB);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createMultNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_MULTIPLY);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createDivNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_DIVIDE);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createModNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_MODULO);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createIfNode(Node condition, Node thenBody, Node elseBody) {
        Node node = new Node();
        node.setType(NodeType.INSTR_IF);
        node.addNode(condition);
        node.addNode(thenBody);
        if (elseBody != null) {
            node.addNode(elseBody);
        }
        return node;
    }

    static private int loopCounter = 0;
    static private String loopPrefix = "!_i_";

    public Node createLoopNode(String name, Node count, List<Node> body) {
        Node node = new Node();
        node.setType(NodeType.INSTR_LOOP);
        if (name == null) {
            name = loopPrefix + loopCounter++;
        }
        NodeValue variableName = new NodeValue(name);
        Node bodyNode = new Node();
        bodyNode.setType(NodeType.LIST);
        bodyNode.addNodes(body);
        node.addNode(createConstantNode(variableName));
        node.addNode(count);
        node.addNode(bodyNode);
        return node;
    }

}
