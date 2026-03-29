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
        } else if (left.getType() == XonValueType.BOOLEAN && right.getType() == XonValueType.BOOLEAN) {
            return new XonValue().setText(left.getBoolean().toString() + right.getBoolean().toString());
        }
        else {
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
        } else if (left.getType() == XonValueType.BOOLEAN && right.getType() == XonValueType.BOOLEAN) {
            return new XonValue().setText(left.getBoolean().toString() + right.getBoolean().toString());
        }
        else {
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
        } else if (left.getType() == XonValueType.BOOLEAN && right.getType() == XonValueType.BOOLEAN) {
            return new XonValue().setText(left.getBoolean().toString() + right.getBoolean().toString());
        }
        else {
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
        } else if (left.getType() == XonValueType.BOOLEAN && right.getType() == XonValueType.BOOLEAN) {
            return new XonValue().setText(left.getBoolean().toString() + right.getBoolean().toString());
        }
        else {
            throw new IllegalArgumentException("Incompatible types for division ");
        }
    }

    static public XonValue negate(XonValue value) throws TransformerException {
        if (value.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(-value.getInteger());
        } else if (value.getType() == XonValueType.REAL) {
            return new XonValue().setReal(-value.getReal());
        } else {
            throw new IllegalArgumentException("Cannot negate type: " + value.getType());
        }
    }

    static public XonValue modulo(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() % right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() % right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() % right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() % right.getReal());
        } else if (left.getType() == XonValueType.BOOLEAN && right.getType() == XonValueType.BOOLEAN) {
            return new XonValue().setText(left.getBoolean().toString() + right.getBoolean().toString());
        }
        else {
            throw new IllegalArgumentException("Incompatible types for modulo ");
        }
    }

    static public XonValue equals(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() != right.getType()) {
            return new XonValue().setBoolean(false);
        } else {
            switch (left.getType()) {
            case INTEGER:
                return new XonValue().setBoolean(left.getInteger().equals(right.getInteger()));
            case REAL:
                return new XonValue().setBoolean(left.getReal().equals(right.getReal()));
            case TEXT:
                return new XonValue().setBoolean(left.getText().equals(right.getText()));
            case BOOLEAN:
                return new XonValue().setBoolean(left.getBoolean().equals(right.getBoolean()));
            default:
                throw new IllegalArgumentException("Unsupported types for equality check: " + left.getType());
            }
        }
    }

    static public XonValue notEquals(XonValue left, XonValue right) throws TransformerException {
        XonValue equalsResult = equals(left, right);
        return new XonValue().setBoolean(!equalsResult.getBoolean());
    }

    static public XonValue lessThan(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getInteger() < right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getReal() < right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getReal() < right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getInteger().doubleValue() < right.getReal());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getText().compareTo(right.getInteger().toString()) < 0);
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getText().compareTo(right.getReal().toString()) < 0);
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.TEXT) {
            return new XonValue().setBoolean(left.getInteger().toString().compareTo(right.getText()) < 0);
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.TEXT) {
            return new XonValue().setBoolean(left.getReal().toString().compareTo(right.getText()) < 0);
        }
        else {
            throw new IllegalArgumentException("Incompatible types for less than comparison ");
        }
    }

    static public XonValue greaterThan(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getInteger() > right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getReal() > right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getReal() > right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getInteger().doubleValue() > right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for greater than comparison ");
        }
    }

    static public XonValue lessThanOrEqual(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getInteger() <= right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getReal() <= right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getReal() <= right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getInteger().doubleValue() <= right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for less than or equal comparison ");
        }
    }

    static public XonValue greaterThanOrEqual(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getInteger() >= right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getReal() >= right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setBoolean(left.getReal() >= right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setBoolean(left.getInteger().doubleValue() >= right.getReal());
        } else {
            throw new IllegalArgumentException("Incompatible types for greater than or equal comparison ");
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
