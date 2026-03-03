package fr.uha.hassenforder.cage.calculator;

import java.io.FileReader;

import fr.uha.page.runtime.SymbolFactory;

public class Main {

	static public void main(String argv[]) {    
    try {
    	String name;
    	if (argv.length == 1) {
    		name = argv[0];
    	} else {
    		name = "fr.uha.hassenforder.cage.calculator/test.txt";
    	}
    	SymbolFactory factory = new SymbolFactory();
    	Scanner scanner = new Scanner(new FileReader(name), factory);
    	Parser parser = new Parser(scanner, factory);
    	parser.parse();      
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
}


