package fr.uha.hassenforder.cage.xon.dump;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class IndentPrinter implements IPrinter {

    private Writer writer;
	private BufferedWriter buffer;
    private int indentLevel = 0;
    private String indentString;
    private boolean atBeginningOfLine = true;

    public IndentPrinter(Writer writer, int indentSpaceCount) {
        this.writer = writer;
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < indentSpaceCount; i++) {
            tmp.append(" ");
        }
        this.indentString = tmp.toString();
    }

    public void start() {
        buffer = new BufferedWriter(writer);
        indentLevel = 0;
        atBeginningOfLine = true;
    }

    public void end() {
        try {
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertIndent() throws DumpException {
        try {
            for (int i = 0; i < indentLevel; i++) {
                buffer.write(indentString);
            }
        } catch (IOException e) {
            throw new DumpException("Error inserting indent: " + e.getMessage());
        }
    }

    public void increase() {
        ++indentLevel;
    }
    
    public void decrease() {
        --indentLevel;
    }
    
    public void print(String text) throws DumpException {
        if (atBeginningOfLine) {
            insertIndent();
            atBeginningOfLine = false;
        }
        try {
            buffer.write(text);
        } catch (IOException e) {
            throw new DumpException("Error printing text: " + e.getMessage());
        }
    }

    public void println() throws DumpException {
        try {
            buffer.newLine();
        } catch (IOException e) {
            throw new DumpException("Error printing newline: " + e.getMessage());
        }
        atBeginningOfLine = true;
    }

    public void println(String text) throws DumpException {
        print(text);
        println();
    }

}
