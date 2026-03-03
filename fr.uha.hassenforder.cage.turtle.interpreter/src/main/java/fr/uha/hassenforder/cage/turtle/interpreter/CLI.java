package fr.uha.hassenforder.cage.turtle.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import fr.uha.hassenforder.cage.turtle.interpreter.processor.Processor;

public class CLI {

	private void processContent () {
		try (
			InputStream inputStream = getClass().getResourceAsStream("view/faulty.ttl");
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream)))
		{
			StringBuilder tmp = new StringBuilder();
			String line;
			while ((line = inputReader.readLine()) != null) {
				tmp.append(line);
				tmp.append("\n");
			}
			Processor processor = new Processor();
			processor.read(new StringReader(tmp.toString()));
			System.out.println(processor.getContext().getAllMessages());
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		CLI cli = new CLI();
		cli.processContent();
	}

}
