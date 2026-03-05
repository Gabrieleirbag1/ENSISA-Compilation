package fr.uha.hassenforder.cage.xon.transformer;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.uha.hassenforder.cage.xon.model.xon.NodeValue;

public class XonValue {

    private XonValueType type;

	private JSONObject object = null;
	private JSONArray array = null;
	private List<XonValue> list = null;
	private Pair<XonValue, XonValue> pair = null;
	private String text = null;
	private Integer integer = null;
	private Double real = null;
    
    public XonValue() {
    }

    public XonValue setInteger(Integer integer) {
        this.type = XonValueType.INTEGER;
        this.integer = integer;
        return this;
    }

    public XonValue setReal(Double real) {
        this.type = XonValueType.REAL;
        this.real = real;
        return this;
    }

    public XonValue setText(String text) {
        this.type = XonValueType.TEXT;
        this.text = text;
        return this;
    }

    public XonValue setObject(JSONObject object) {
        this.type = XonValueType.OBJECT;
        this.object = object;
        return this;
    }
    
    public XonValue setArray(JSONArray array) {
        this.type = XonValueType.ARRAY;
        this.array = array;
        return this;
    }
    
    public XonValue setList(List<XonValue> list) {
        this.type = XonValueType.LIST;
        this.list = list;
        return this;
    }
    
    public XonValue setPair(Pair<XonValue, XonValue> pair) {
        this.type = XonValueType.PAIR;
        this.pair = pair;
        return this;
    }
    
    public XonValueType getType() {
        return type;
    }

    public JSONObject getObject() {
        return object;
    }
    
    public JSONArray getArray() {
        return array;
    }
    
    public List<XonValue> getList() {
        return list;
    }
    
    public Pair<XonValue, XonValue> getPair() {
        return pair;
    }
    
    public String getText() {
        return text;
    }

    public Integer getInteger() {
        return integer;
    }
    
    public Double getReal() {
        return real;
    }

    public Object getContent() {
        switch (type) {
        case OBJECT:
            return object;
        case ARRAY:
            return array;
        case LIST:
            return list;
        case PAIR:
            return pair;
        case TEXT:
            return text;
        case INTEGER:
            return integer;
        case REAL:
            return real;
        default:
            throw new UnsupportedOperationException("Unimplemented method 'getContent'");
        }
    }

    public XonValue setValue(NodeValue value) {
        XonValue result = new XonValue();
        if (value == null) {
            throw new UnsupportedOperationException("null value in 'setValue'");
        }
        if (value.isInteger()) {
            result.setInteger(value.asInteger());
        } else if (value.isReal()) {
            result.setReal(value.asReal());
        } else if (value.isText()) {
            result.setText(value.asText());
        } else {
            throw new UnsupportedOperationException("Unsupported value type in 'setValue': " + value);
        }
        return result;
    }

    @Override
    public String toString() {
        return "XonValue [type=" + type + "]";
    }

}