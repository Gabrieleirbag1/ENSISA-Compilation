package fr.uha.hassenforder.cage.xon.dump;

import java.io.Writer;

import fr.uha.hassenforder.cage.xon.model.xon.Node;
import fr.uha.hassenforder.cage.xon.model.xon.NodeVisitor;

public class XonDumper extends NodeVisitor implements IPrinter {

    private IndentPrinter printer;
    
    public XonDumper(Writer writer) {
        this.printer = new IndentPrinter(writer, 3);
    }

    @Override
    public void start() {
        printer.start();
    }

    @Override
    public void end() {
        printer.end();
    }

    public void write(Node root) {
        start();
        visit_Node(root);
        end();
    }

    private void visit_AnyNode(String header, Node node, String trailer) throws DumpException {
        printer.println(header);
        printer.increase();
        for (Node child : node.getChildren()) {
            visit_Node(child);
        }
        printer.decrease();
        printer.println(trailer);
    }

    @Override
    public void visit_JsonNode(Node node) {
        visit_AnyNode("JSON {", node, "JSON }");
    }

    @Override
    public void visit_ObjectNode(Node node) throws DumpException {
        visit_AnyNode("OBJ {", node, "OBJ }");
    }

    @Override
    public void visit_ArrayNode(Node node) throws DumpException{
        visit_AnyNode("ARRAY [", node, "ARRAY ]");
    }

    @Override
    public void visit_ListNode(Node node) throws DumpException{
        visit_AnyNode("LIST", node, "LIST");
    }

    @Override
    public void visit_PairNode(Node node) throws DumpException  {
        visit_AnyNode("PAIR", node, "PAIR");
    }

    @Override
    public void visit_ConstantNode(Node node) {
        printer.println("CONSTANT " + node.getValue().asText());
    }

    @Override
    public void visit_VariableNode(Node node) {
        printer.println("VARIABLE " + node.getValue().asText());
    }

    @Override
    public void visit_LetNode(Node node) {
        visit_AnyNode("LET", node, "LET");
    }

    @Override
    public void visit_GetNode(Node node)  {
        visit_AnyNode("GET", node, "GET");
    }

    @Override
    public void visit_Divide(Node node) throws DumpException{
        visit_AnyNode("DIVIDE", node, "DIVIDE");
    }

    @Override
    public void visit_Multiply(Node node) throws DumpException{
        visit_AnyNode("MULTIPLY", node, "MULTIPLY");
    }

    @Override
    public void visit_Add(Node node) throws DumpException{
        visit_AnyNode("ADD", node, "ADD");
    }

    @Override
    public void visit_Sub(Node node) throws DumpException{
        visit_AnyNode("SUB", node, "SUB");
    }

    @Override
    public void visit_Modulo(Node node) throws DumpException{
        visit_AnyNode("MODULO", node, "MODULO");
    }

    @Override
    public void visit_Ternary(Node node) throws DumpException{
        visit_AnyNode("TERNARY", node, "TERNARY");
    }

    @Override
    public void visit_Loop(Node node) throws DumpException{
        visit_AnyNode("LOOP", node, "LOOP");
    }

    @Override
    public void visit_While(Node node) throws DumpException{
        visit_AnyNode("WHILE", node, "WHILE");
    }

    @Override
    public void visit_If(Node node) {
        visit_AnyNode("IF", node, "IF");
    }

    @Override
    public void visit_Equals(Node node) throws DumpException {
        visit_AnyNode("EQUALS", node, "EQUALS");
    }

    @Override
    public void visit_NotEquals(Node node) throws DumpException {
        visit_AnyNode("NOT_EQUALS", node, "NOT_EQUALS");
    }

    @Override
    public void visit_LessThan(Node node) throws DumpException {
        visit_AnyNode("LESS_THAN", node, "LESS_THAN");
    }

    @Override
    public void visit_GreaterThan(Node node) throws DumpException {
        visit_AnyNode("GREATER_THAN", node, "GREATER_THAN");
    }

    @Override
    public void visit_LessThanOrEqual(Node node) throws DumpException {
        visit_AnyNode("LESS_THAN_OR_EQUAL", node, "LESS_THAN_OR_EQUAL");
    }

    @Override
    public void visit_GreaterThanOrEqual(Node node) throws DumpException {
        visit_AnyNode("GREATER_THAN_OR_EQUAL", node, "GREATER_THAN_OR_EQUAL");
    }

}
