package fr.uha.hassenforder.cage.turtle.compiler.writer.svg;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import fr.uha.hassenforder.ast.Node;
import fr.uha.hassenforder.ast.Value;
import fr.uha.hassenforder.ast.ValueID;
import fr.uha.hassenforder.ast.evaluator.Evaluator;
import fr.uha.hassenforder.turtle.visitor.TurtleVisitor;

public class SVGGenerator extends TurtleVisitor {
	
	private String outputFileName;
	private Evaluator eval;
	private SVGTurtle turtle;
	private PrintWriter writer;
	
	public SVGGenerator(String outputFileName, Evaluator evaluator) {
		this.eval = evaluator;
		this.outputFileName = outputFileName;
	}

	private long getValueAsLong (Value v) {
		if (v.getValueID() == ValueID.VALUE_INTEGER)
			return v.getLongValue();
		if (v.getValueID() == ValueID.VALUE_REAL)
			return (long) v.getDoubleValue();
		return 0;
	}
	
	private String getValueAsString (Value v) {
		if (v.getValueID() == ValueID.VALUE_INTEGER)
			return Long.toString(v.getLongValue());
		if (v.getValueID() == ValueID.VALUE_REAL)
			return Double.toString(v.getDoubleValue());
		if (v.getValueID() == ValueID.VALUE_STRING)
			return v.getStringValue();
		return "no-value";
	}

	private void openWriter () {
		try {
			if (outputFileName != null && ! outputFileName.isEmpty()) {
				this.writer = new PrintWriter (outputFileName);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			outputFileName = null;
		}
		if (this.writer == null) {
			this.writer = new PrintWriter(System.out);
		}
		this.turtle = new SVGTurtle(writer);
	}

	private void emitHeader() {
		writer.println("<?xml version='1.0' encoding='utf-16'?>");
		writer.println("<svg:svg viewBox='0 0 1024 800' xmlns:svg='http://www.w3.org/2000/svg'>");
		writer.println("<svg:g  >");
	}

	@Override
	public void begin() {
		super.begin();
		openWriter();
		emitHeader ();
	}

	private void closeWriter () {
		if (outputFileName != null && ! outputFileName.isEmpty()) {
			this.writer.close();
		}
	}
	
	private void emitTrailer() {
		writer.println("</svg:g>");
		writer.println("</svg:svg>");
	}

	@Override
	public void end() {
		super.end();
		emitTrailer();
		closeWriter();
	}

	@Override
	protected void visit_TurtleDown(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.down((int) d);
//		System.out.println("Down : "+d);
	}

	@Override
	protected void visit_TurtleFrame(Node node) {
		eval.visit(node.getChildren().get(0));
		long x = getValueAsLong (eval.getRealValue());
		eval.visit(node.getChildren().get(1));
		long y = getValueAsLong (eval.getRealValue());
		turtle.frame((int) x, (int) y);
	}

	@Override
	protected void visit_TurtleGo(Node node) {
		eval.visit(node.getChildren().get(0));
		long x = getValueAsLong (eval.getRealValue());
		eval.visit(node.getChildren().get(1));
		long y = getValueAsLong (eval.getRealValue());
		turtle.go((int) x, (int) y);
//		System.out.println("Go : "+x+"@"+y);
	}

	@Override
	protected void visit_TurtleHome(Node node) {
		turtle.home();
	}

	@Override
	protected void visit_TurtleLeft(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.left((int) d);
//		System.out.println("Left : "+d);
	}

	@Override
	protected void visit_TurtleMove(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.move((int) d);
//		System.out.println("Move : "+d);
	}

	@Override
	protected void visit_TurtleOff(Node node) {
		turtle.penUp();
	}

	@Override
	protected void visit_TurtleOn(Node node) {
		turtle.penDown();
	}

	@Override
	protected void visit_TurtleRight(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.right((int) d);
//		System.out.println("Right : "+d);
	}

	@Override
	protected void visit_TurtleTurn(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.turn((int) d);
//		System.out.println("Turn : "+d);
	}

	@Override
	protected void visit_TurtleUp(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.up((int) d);
//		System.out.println("Up : "+d);
	}

	@Override
	protected void visit_TurtleInk(Node node) {
		eval.visit(node.getChildren().get(0));
		long d = getValueAsLong (eval.getRealValue());
		turtle.ink((int) d);
	}

	@Override
	protected void visit_TurtlePlot(Node node) {
		eval.visit(node.getChildren().get(0));
		String s = getValueAsString (eval.getRealValue());
		turtle.plot(s);
	}

}
