package fr.uha.hassenforder.cage.turtle.interpreter.processor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import fr.uha.hassenforder.cage.turtle.interpreter.LogoContext;
import fr.uha.hassenforder.cage.turtle.interpreter.reader.ttl.Parser;
import fr.uha.hassenforder.cage.turtle.interpreter.reader.ttl.Scanner;
import fr.uha.hassenforder.cage.turtle.interpreter.renderer.LogoRender;
import fr.uha.hassenforder.cage.turtle.interpreter.view.GraphicTurtle;
import fr.uha.page.runtime.ISymbolFactory;
import fr.uha.page.runtime.SymbolFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Processor {

	private boolean displayTiming = false;
	private LogoContext context = null;

	public Processor() {
		this.displayTiming = false;
	}

	public Processor(boolean displayTiming) {
		this.displayTiming = displayTiming;
	}

	public LogoContext getContext() {
		return context;
	}

	public void read(Reader input) {
		long startTime = System.currentTimeMillis();
		if (displayTiming) {
			System.out.println ("Reading process started : "+startTime+" ms");
		}
		context = new LogoContext();
    	ISymbolFactory symbolFactory = new SymbolFactory ();
    	Scanner scanner = new Scanner(input, symbolFactory);
    	PrintStream previousOut = System.out;
    	PrintStream previousErr = System.err;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
    	try {
        	System.setOut(printStream);
        	System.setErr(printStream);
			Parser parser = new Parser(scanner, symbolFactory);
			parser.setContext(context);
	    	parser.parse();
        	System.out.println("Inputs processed successfully");
	    } catch (Exception e) {
	    	e.printStackTrace();
        	System.err.println("Failed processing inputs");
	    }
    	System.setOut(previousOut);
    	System.setErr(previousErr);
		printStream.flush();
		context.setStdout(baos.toString());
		printStream.close();
		try { baos.close(); } catch (IOException e) { }
		long endTime = System.currentTimeMillis();
		if (displayTiming) {
			System.out.println ("Reading process ended : "+endTime+" ms");
			System.out.println ("Reading process took : "+(endTime-startTime)+" ms");
		}
	}

    public void draw (Canvas drawingArea) {
		long startTime = System.currentTimeMillis();
		if (displayTiming) {
			System.out.println ("Drawing process started : "+startTime+" ms");
		}
    	GraphicsContext gc = drawingArea.getGraphicsContext2D();
		GraphicTurtle gt = new GraphicTurtle(gc);
		if (context != null && ! context.hasErrors()) {
			LogoRender render = new LogoRender(context);
			render.render(gt);
		}
		long endTime = System.currentTimeMillis();
		if (displayTiming) {
			System.out.println ("Drawing process ended : "+endTime+" ms");
			System.out.println ("Drawing process took : "+(endTime-startTime)+" ms");
		}
    }

}
