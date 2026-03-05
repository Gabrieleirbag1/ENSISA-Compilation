package fr.uha.hassenforder.cage.xon.transformer;

import java.util.ArrayList;
import java.util.List;

public class XonOperators {

    static public XonValue addition(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() + right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() + right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() + right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() + right.getReal());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.TEXT) {
            return new XonValue().setText(left.getText() + right.getText());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setText(left.getText() + right.getInteger().toString());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.REAL) {
            return new XonValue().setText(left.getText() + right.getReal().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for addition ");
        }
    }

    static public XonValue substract(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() - right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() - right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() - right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() - right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for subtraction ");
        }
    }

    static public XonValue multiply(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() * right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() * right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() * right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() * right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for multiplication ");
        }
    }

    static public XonValue divide(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() / right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() / right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() / right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() / right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for division ");
        }
    }

    static public XonValue modulo(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() % right.getInteger());
        } else {
            throw new IllegalArgumentException("Incompatible types for modulo ");
        }
    }

    static public List<XonValue> unwrap (List<XonValue> initial) {
        List<XonValue> unwrapped = new ArrayList<XonValue>();
        for (XonValue value : initial) {
            if (value.getType() == XonValueType.LIST) {
                unwrapped.addAll(unwrap(value.getList()));
            } else {
                unwrapped.add(value);
            }
        }
        return unwrapped;
    }


}
