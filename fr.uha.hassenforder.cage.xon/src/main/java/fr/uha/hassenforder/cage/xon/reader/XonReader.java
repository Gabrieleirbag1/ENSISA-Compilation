package fr.uha.hassenforder.cage.xon.reader;

import java.io.Reader;

import fr.uha.hassenforder.cage.xon.Parser;
import fr.uha.hassenforder.cage.xon.Scanner;
import fr.uha.hassenforder.cage.xon.model.xon.Node;
import fr.uha.page.runtime.ISymbol;
import fr.uha.page.runtime.SymbolFactory;

public class XonReader {

    public XonReader() {
    }

    public Node read(Reader reader) {
        try {
            SymbolFactory factory = new SymbolFactory();
            Scanner scanner = new Scanner(reader, factory);
            Parser parser = new Parser(scanner, factory);
            ISymbol root = parser.parse();
            return (Node) root.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}