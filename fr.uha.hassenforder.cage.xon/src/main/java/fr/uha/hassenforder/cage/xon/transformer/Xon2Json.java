package fr.uha.hassenforder.cage.xon.transformer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.uha.hassenforder.cage.xon.model.xon.Node;
import fr.uha.hassenforder.cage.xon.model.xon.NodeVisitor;

public class Xon2Json extends NodeVisitor {

	private Map<String, XonValue> values = new TreeMap<String, XonValue> ();

    private XonValue result = null;
    
	public Xon2Json() {
    }

    private XonValue getResult() {
        return result;
    }

    private void setResult(XonValue value) {
        this.result = value;
    }

    private void setValue (String name, XonValue value) {
		values.put(name, value);
	}
	
	private XonValue getValue (String name) {
		if (! values.containsKey(name)) {
            throw new TransformerException("Undefined variable: " + name);
        }
		return values.get(name);
	}

    public JSONObject transform(Node node) {
        visit_Node(node);
        XonValue result = getResult();
        if (result.getType() != XonValueType.OBJECT) {
            throw new TransformerException("Expected an object as root but got: " + result);
        }
        return result.getObject();
    }

    @Override
    public void visit_JsonNode(Node node) {
        List<XonValue> children = visit_Children(node);
        if (children.size() != 1) {
            throw new TransformerException("Expected exactly 1 child for a json node but got: " + children.size());
        }
        setResult(children.get(0));
    }   

    @Override
    public void visit_ObjectNode(Node node) {
        List<XonValue> children = visit_Children(node);
        JSONObject json = new JSONObject();
        for (XonValue child : XonOperators.unwrap(children)) {
            if (child.getType() == XonValueType.PAIR) {
                XonValue key = child.getPair().getKey();
                if (key.getType() != XonValueType.TEXT) {
                    throw new TransformerException("Expected a text as key but got: " + key);
                }
                XonValue value = child.getPair().getValue();
                json.put(key.getText(), value.getContent());
            } else {
                throw new TransformerException("Expected a pair but got: " + child);
            }
        }
        setResult(new XonValue().setObject(json));
    }

    @Override
    public void visit_ArrayNode(Node node) {
        List<XonValue> children = visit_Children(node);
        JSONArray array = new JSONArray();
        for (XonValue child : XonOperators.unwrap(children)) {
            array.put(child.getContent());
        }
        setResult(new XonValue().setArray(array));
    }

    @Override
    public void visit_PairNode(Node node) {
        List<XonValue> children = visit_Children(node);
        if (children.size() != 2) {
            throw new TransformerException("Expected exactly 2 children for a pair but got: " + children.size());
        }
        setResult(new XonValue().setPair(new Pair<XonValue, XonValue>(children.get(0), children.get(1))));
    }

    @Override
    public void visit_ListNode(Node node) {
        List<XonValue> children = visit_Children(node);
        setResult(new XonValue().setList(XonOperators.unwrap(children)));
    }

    @Override
    public void visit_ConstantNode(Node node) {
        setResult(new XonValue().setValue(node.getValue()));
    }

    @Override
    public void visit_VariableNode(Node node) {
        String name = node.getValue().asText();
        setResult(getValue(name));
    }

    @Override
    public void visit_LetNode(Node node) {
        // first node is variable name
        Node variableNameNode = node.getChildren().get(0);
        visit_Node(variableNameNode);
        XonValue variableName = getResult();
        if (variableName.getType() != XonValueType.TEXT) {
            throw new TransformerException("Expected a text as variableName but got: " + variableName);
        }
        // second node is value
        Node valueNode = node.getChildren().get(1);
        visit_Node(valueNode);
        XonValue value = getResult();
        // set variable in table
        setValue(variableName.getText(), value);
        // obviously let node does not produce value
        setResult(null);
    }

    @Override
    public void visit_GetNode(Node node) {
        // first node is variable name
        Node variableNameNode = node.getChildren().get(0);
        visit_Node(variableNameNode);
        XonValue variableName = getResult();
        if (variableName.getType() != XonValueType.TEXT) {
            throw new TransformerException("Expected a text as variableName but got: " + variableName);
        }
        setResult(getValue(variableName.getText()));
    }

    private List<XonValue> visit_Children(Node node) {
        List<XonValue> values = new java.util.ArrayList<XonValue>();
        for (Node child : node.getChildren()) {
            visit_Node(child);
            if (getResult() != null) {
                values.add(getResult());
            }
        }
        return values;
    }

    @Override
    public void visit_Divide(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.divide(leftValue, rightValue));
    }

    @Override
    public void visit_Multiply(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.multiply(leftValue, rightValue));
    }

    @Override
    public void visit_Add(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.addition(leftValue, rightValue));
    }

    @Override
    public void visit_Sub(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.substract(leftValue, rightValue));
    }

    @Override
    public void visit_Equals(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.equals(leftValue, rightValue));
    }

    @Override
    public void visit_NotEquals(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.notEquals(leftValue, rightValue));
    }

    @Override
    public void visit_LessThan(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.lessThan(leftValue, rightValue));
    }

    @Override
    public void visit_GreaterThan(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.greaterThan(leftValue, rightValue));
    }

    @Override
    public void visit_LessThanOrEqual(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.lessThanOrEqual(leftValue, rightValue));
    }

    @Override
    public void visit_GreaterThanOrEqual(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.greaterThanOrEqual(leftValue, rightValue));
    }

    @Override
    public void visit_Modulo(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue leftValue = values.get(0);
        XonValue rightValue = values.get(1);
        setResult(XonOperators.modulo(leftValue, rightValue));
    }

    @Override
    public void visit_Ternary(Node node) throws TransformerException{
        setResult(null);
    }

    @Override
    public void visit_If(Node node) throws TransformerException{
        List<XonValue> values = visit_Children(node);
        XonValue conditionExpression = values.get(0);
        XonValue thenValue = values.get(1);
        XonValue elseValue = values.get(2);
        
        if (conditionExpression.getType() != XonValueType.BOOLEAN) {
            throw new TransformerException("Expected a boolean as condition but got: " + conditionExpression);
        }

        if ((Boolean) conditionExpression.getContent()) {
            setResult(thenValue);
        } else {
            setResult(elseValue);
        }

    }

    private XonValue extractLastResult(XonValue result) {
        if (result != null && result.getType() == XonValueType.LIST) {
            List<XonValue> statements = result.getList();
            if (!statements.isEmpty()) {
                return statements.get(statements.size() - 1);
            }
        }
        return result;
    }

    private XonValue convertJsonToXonValue(Object jsonElement) throws TransformerException {
        if (jsonElement instanceof Integer) {
            return new XonValue().setInteger((Integer) jsonElement);
        } else if (jsonElement instanceof Double) {
            return new XonValue().setReal((Double) jsonElement);
        } else if (jsonElement instanceof String) {
            return new XonValue().setText((String) jsonElement);
        } else if (jsonElement instanceof Boolean) {
            return new XonValue().setBoolean((Boolean) jsonElement);
        } else if (jsonElement instanceof JSONObject) {
            return new XonValue().setObject((JSONObject) jsonElement);
        } else if (jsonElement instanceof JSONArray) {
            return new XonValue().setArray((JSONArray) jsonElement);
        } else if (jsonElement == JSONObject.NULL) {
            throw new TransformerException("Cannot iterate over null value");
        } else {
            throw new TransformerException(
                "Unsupported array element type: " + jsonElement.getClass().getName()
            );
        }
    }

    @Override
    public void visit_Loop(Node node) throws TransformerException {
        // Child 0: Extract loop variable name
        Node varNameNode = node.getChildren().get(0);
        visit_Node(varNameNode);
        XonValue varNameValue = getResult();
        String loopVarName = varNameValue.getText();

        // Child 1: Evaluate iteration expression
        Node iterNode = node.getChildren().get(1);
        visit_Node(iterNode);
        XonValue iterValue = getResult();

        // Child 2: Loop body
        Node bodyNode = node.getChildren().get(2);

        List<XonValue> allResults = new java.util.ArrayList<>();

        // MODE 1: INTEGER - counting mode
        if (iterValue.getType() == XonValueType.INTEGER) {
            int count = (Integer) iterValue.getContent();
            for (int i = 0; i < count; i++) {
                visit_Node(bodyNode);
                XonValue iterResult = extractLastResult(getResult());
                if (iterResult != null) {
                    allResults.add(iterResult);
                }
            }
        }
        // MODE 2: ARRAY - iteration mode
        else if (iterValue.getType() == XonValueType.ARRAY) {
            JSONArray array = iterValue.getArray();
            for (int i = 0; i < array.length(); i++) {
                // Convert array element to XonValue
                Object element = array.get(i);
                XonValue elementValue = convertJsonToXonValue(element);

                // Set loop variable to current element
                setValue(loopVarName, elementValue);

                // Execute body
                visit_Node(bodyNode);
                XonValue iterResult = extractLastResult(getResult());
                if (iterResult != null) {
                    allResults.add(iterResult);
                }
            }
        }
        else {
            throw new TransformerException(
                "Loop iteration value must be INTEGER or ARRAY, got: " + iterValue.getType()
            );
        }

        // Return array containing all iteration results
        JSONArray resultArray = new JSONArray();
        for (XonValue val : allResults) {
            resultArray.put(val.getContent());
        }
        setResult(new XonValue().setArray(resultArray));
    }

    @Override
    public void visit_While(Node node) throws TransformerException {
        // Child 0: Evaluate condition expression
        Node conditionNode = node.getChildren().get(0);
        Node bodyNode = node.getChildren().get(1);

        List<XonValue> allResults = new java.util.ArrayList<>();

        while (true) {
            visit_Node(conditionNode);
            XonValue conditionValue = getResult();
            if (conditionValue.getType() != XonValueType.BOOLEAN) {
                throw new TransformerException("Expected a boolean as condition but got: " + conditionValue);
            }
            if (! (Boolean) conditionValue.getContent()) {
                break;
            }
            visit_Node(bodyNode);
            XonValue iterResult = extractLastResult(getResult());
            if (iterResult != null) {
                allResults.add(iterResult);
            }
        }

        // Return array containing all iteration results
        JSONArray resultArray = new JSONArray();
        for (XonValue val : allResults) {
            resultArray.put(val.getContent());
        }
        setResult(new XonValue().setArray(resultArray));
    }
}
