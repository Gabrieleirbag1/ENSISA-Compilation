package fr.uha.hassenforder.cage.turtle.compiler.writer.svg;

import java.awt.Color;
import java.io.PrintWriter;

import fr.uha.hassenforder.cage.turtle.compiler.view.Turtle;

public class SVGTurtle {

	private Color color;
	private PrintWriter p;
	private Turtle turtle;
	private int oldx, oldy;
	
	public SVGTurtle (PrintWriter p) {
		this.p = p;
		this.turtle = new Turtle ();
		this.home();
	}

	public void frame (int w, int h) {
		turtle.frame (w, h);
		p.println("");
		StringBuffer tmp = new StringBuffer();
		tmp.append("<svg:rect ");
		tmp.append("x='10' ");
		tmp.append("y='10' ");
		tmp.append("width='");
		tmp.append(w-10);
		tmp.append("' ");
		tmp.append("height='");
		tmp.append(h-10);
		tmp.append("' ");
		tmp.append("style='stroke:black;fill:white;stroke-width:1");
		tmp.append("'/>");
		p.println(tmp.toString());
		turtle.home ();
	}

	public void home () {
		turtle.home ();
		color = new Color (0x00000000);
		oldx = turtle.getX();
		oldy = turtle.getY();
	}

	private void drawLine () {
		if (turtle.isPrinting()) {
			StringBuffer tmp = new StringBuffer();
			tmp.append("<svg:line ");
			tmp.append("x1='");
			tmp.append(oldx);
			tmp.append("' ");
			tmp.append("y1='");
			tmp.append(oldy);
			tmp.append("' ");
			tmp.append("x2='");
			tmp.append(turtle.getX());
			tmp.append("' ");
			tmp.append("y2='");
			tmp.append(turtle.getY());
			tmp.append("' ");
			tmp.append("style='stroke:rgb(");
			tmp.append(color.getRed());
			tmp.append(", ");
			tmp.append(color.getGreen());
			tmp.append(", ");
			tmp.append(color.getBlue());
			tmp.append(");stroke-width:2");
			tmp.append("'/>");
			p.println(tmp.toString());
		}
	}

	private void drawText (String text) {
		if (turtle.isPrinting()) {
			StringBuffer tmp = new StringBuffer();
			tmp.append("<svg:text ");
			tmp.append("x='");
			tmp.append(turtle.getX());
			tmp.append("' ");
			tmp.append("y='");
			tmp.append(turtle.getY());
			tmp.append("' ");
			tmp.append("style='fill(");
			tmp.append(color.getRed());
			tmp.append(", ");
			tmp.append(color.getGreen());
			tmp.append(", ");
			tmp.append(color.getBlue());
			tmp.append(");");
			tmp.append("'>");
			tmp.append(text);
			tmp.append("</svg:text>");
			p.println(tmp.toString());
		}
	}

	private void shift (int x, int y) {
		oldx = turtle.getX();
		oldy = turtle.getY();
		turtle.shift (x, y);
		drawLine ();
	}

	public void up (int d) {
		shift (0, d);
	}

	public void down (int d) {
		shift (0, -d);
	}

	public void right (int d) {
		shift (d, 0);
	}

	public void left (int d) {
		shift (-d, 0);
	}

	public void go (int x, int y) {
		oldx = turtle.getX();
		oldy = turtle.getY();
		turtle.go (x, y);
		drawLine ();
	}

	public void move (int d) {
		oldx = turtle.getX();
		oldy = turtle.getY();
		turtle.move (d);
		drawLine ();
	}
	
	public void turn (int a) {
		turtle.turn (a);
	}

	public void penDown () {
		turtle.penDown();
	}
	
	public void penUp () {
		turtle.penUp();
	}
	
	public void ink (int ink) {
		color = new Color(ink);
	}

	public void plot (String text) {
		drawText(text);
	}
}
