package fr.uha.hassenforder.cage.turtle.interpreter.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphicTurtle {

	private GraphicsContext gc;
	private Turtle turtle;
	private int oldx, oldy;
	
	public GraphicTurtle (GraphicsContext gc) {
		this.gc = gc;
		this.turtle = new Turtle ();
		int w = (int) gc.getCanvas().getWidth();
		int h = (int) gc.getCanvas().getHeight();
		frame (w, h);
		home ();
	}

	public void frame (int w, int h) {
		turtle.frame (w, h);
		Paint oldPaint = gc.getFill();
		gc.setFill(Color.LIGHTCYAN);
		gc.fillRect(1, 1, w-2, h-2);
		gc.setFill(oldPaint);
		Color color = Color.rgb(240, 0, 240, 1.0);
		gc.setStroke(color);
		gc.strokeRect(1, 1, w-2, h-2);
	}

	public void home () {
		turtle.home ();
		oldx = turtle.getX();
		oldy = turtle.getY();
		Paint oldPaint = gc.getFill();
		gc.setFill(Color.BLUE);
		gc.fillOval(turtle.getX(), turtle.getY(), 5, 5);
		gc.setFill(oldPaint);
	}

	private void drawLine () {
		if (turtle.isPrinting()) {
			gc.strokeLine(oldx, oldy, turtle.getX(), turtle.getY());
		}
	}

	private void drawText (String text) {
		if (turtle.isPrinting()) {
			gc.fillText(text, turtle.getX(), turtle.getY());
		}
	}

	public void shift (int x, int y) {
		oldx = turtle.getX();
		oldy = turtle.getY();
		turtle.shift (x, y);
		drawLine ();
	}

	public void north (int d) {
		shift (0, d);
	}

	public void south (int d) {
		shift (0, -d);
	}

	public void east (int d) {
		shift (d, 0);
	}

	public void west (int d) {
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

	public void down () {
		turtle.penDown();
	}
	
	public void up () {
		turtle.penUp();
	}
	
	public void ink (int ink) {
		int red   = (ink >> 16) & 0xFF;
		int green = (ink >>  8) & 0xFF;
		int blue  = (ink >>  0) & 0xFF;
		gc.setStroke(Color.rgb(red, green, blue));
	}

	public void plot (String text) {
		drawText(text);
	}
}
