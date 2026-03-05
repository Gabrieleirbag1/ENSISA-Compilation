package fr.uha.hassenforder.cage.xon.model.xon;

public class NodeValue {

	private String text = null;
	private Integer integer = null;
	private Double real = null;
    
    public NodeValue(String text) {
        this.text = text;
    }

    public NodeValue(Integer integer) {
        this.integer = integer;
    }

    public NodeValue(Double real) {
        this.real = real;
    }

    public boolean isText() {
        return text != null;
    }
    
    public boolean isInteger() {
        return integer != null;
    }
    
    public boolean isReal() {
        return real != null;
    }
    
    public String asText() {
        if (text != null) return text;
        if (integer != null) return integer.toString();
        if (real != null) return real.toString();  
        return null;
    }
    
    public int asInteger() {
        if (text != null) return Integer.parseInt(text);
        if (integer != null) return integer.intValue();
        if (real != null) return real.intValue();  
        return 0;
    }
    
    public double asReal() {
        if (text != null) return Double.parseDouble(text);
        if (integer != null) return integer.doubleValue();
        if (real != null) return real.doubleValue();  
        return 0.0;
    }

    public static NodeValue add(NodeValue left, NodeValue right) {
        if (left.isInteger() && right.isInteger()) {
            return new NodeValue(left.asInteger() + right.asInteger());
        } else if (left.isReal() && right.isReal()) {
            return new NodeValue(left.asReal() + right.asReal());
        } else if (left.isReal() && right.isInteger()) {
            return new NodeValue(left.asReal() + right.asReal());
        } else if (left.isInteger() && right.isReal()) {
            return new NodeValue(left.asReal() + right.asReal());
        } else if (left.isText()) {
            return new NodeValue(left.asText() + right.asText());
        } else {
            throw new IllegalArgumentException("Incompatible types for addition ");
        }
    }

    public static NodeValue sub(NodeValue left, NodeValue right) {
        if (left.isInteger() && right.isInteger()) {
            return new NodeValue(left.asInteger() - right.asInteger());
        } else if (left.isReal() && right.isReal()) {
            return new NodeValue(left.asReal() - right.asReal());
        } else if (left.isReal() && right.isInteger()) {
            return new NodeValue(left.asReal() - right.asReal());
        } else if (left.isInteger() && right.isReal()) {
            return new NodeValue(left.asReal() - right.asReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for subtraction ");
        }
    }

    public static NodeValue mult(NodeValue left, NodeValue right) {
        if (left.isInteger() && right.isInteger()) {
            return new NodeValue(left.asInteger() * right.asInteger());
        } else if (left.isReal() && right.isReal()) {
            return new NodeValue(left.asReal() * right.asReal());
        } else if (left.isReal() && right.isInteger()) {
            return new NodeValue(left.asReal() * right.asReal());
        } else if (left.isInteger() && right.isReal()) {
            return new NodeValue(left.asReal() * right.asReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for multiplication ");
        }
    }

    public static NodeValue div(NodeValue left, NodeValue right) {
        if (left.isInteger() && right.isInteger()) {
            return new NodeValue(left.asInteger() / right.asInteger());
        } else if (left.isReal() && right.isReal()) {
            return new NodeValue(left.asReal() / right.asReal());
        } else if (left.isReal() && right.isInteger()) {
            return new NodeValue(left.asReal() / right.asReal());
        } else if (left.isInteger() && right.isReal()) {
            return new NodeValue(left.asReal() / right.asReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for division ");
        }
    }

    public static NodeValue mod(NodeValue left, NodeValue right) {
        if (left.isInteger() && right.isInteger()) {
            return new NodeValue(left.asInteger() % right.asInteger());
        } else {
            throw new IllegalArgumentException("Incompatible types for modulo ");
        }
    }
}
