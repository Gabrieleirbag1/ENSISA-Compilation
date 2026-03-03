package fr.uha.hassenforder.cage.turtle.compiler.view;

public class Turtle {
	
	static final double deg2rad = 2.0 * Math.PI / 360.0;

	private int w, h;
	private int x, y;
	private int angle;
	private boolean print;

	public Turtle () {
	}

	public void frame (int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void home () {
		this.x = w/2;
		this.y = h/2;
		this.angle = 0;
		this.print = true;
	}

	public void shift (int x, int y) {
		this.x += x;
		this.y -= y;
		check ();
	}

	public void go (int x, int y) {
		this.x = x;
		this.y = y;
		check ();
	}

	public void move (int d) {
		this.x += (int) (d * Math.cos(deg2rad*angle));
		this.y += (int) (d * Math.sin(deg2rad*angle));
		check ();
	}

	public void turn (int angle) {
		this.angle += angle;
	}

	public void penDown () {
		this.print = true;
	}

	public void penUp () {
		this.print = false;
	}

	public boolean isPrinting () {
		return this.print;
	}

	public int getX () {
		return this.x;
	}

	public int getY () {
		return this.y;
	}

	public void check() {
		if (this.x < 0) this.x = 0;
		if (this.x > this.w) this.x = this.w - 1;
		if (this.y < 0) this.y = 0; 
		if (this.y > this.h) this.y = this.h - 1;
	}

}
