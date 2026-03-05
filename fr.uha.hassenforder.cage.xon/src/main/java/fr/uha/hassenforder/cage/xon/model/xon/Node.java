package fr.uha.hassenforder.cage.xon.model.xon;

import java.util.ArrayList;
import java.util.List;

public final class Node {

	private NodeType type;
	private NodeValue value;
	private List<Node> children;
	
	Node() {
		super();
	}

	void setType(NodeType type) {
		this.type = type;
	}

	void setValue(NodeValue value) {
		this.value = value;
	}

	void addNodes (List<Node> children) {
		for (Node child : children) {
			getChildren().add(child);
		}
	}
	
	void addNode (Node child) {
		getChildren().add (child);
	}

	public NodeType getType() {
		return type;
	}
	
	public NodeValue getValue() {
		return value;
	}

	public final List<Node> getChildren() {
		if (children == null) children = new ArrayList<Node>();
		return children;
	}
	
}
