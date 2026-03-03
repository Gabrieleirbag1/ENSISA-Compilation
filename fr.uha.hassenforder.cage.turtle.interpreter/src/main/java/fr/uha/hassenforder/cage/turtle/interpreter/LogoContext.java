package fr.uha.hassenforder.cage.turtle.interpreter;

import fr.uha.hassenforder.logo.factory.LogoFactory;
import fr.uha.hassenforder.logo.factory.impl.LogoFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import fr.uha.hassenforder.ast.Helper;
import fr.uha.hassenforder.ast.Node;
import fr.uha.hassenforder.ast.Symbol;
import fr.uha.hassenforder.ast.SymbolFunction;
import fr.uha.hassenforder.ast.SymbolVista;

public class LogoContext {

	private List<String> errors;
	private LogoFactory factory = null;
	private SymbolVista vista = null;
	private String stdout;
	
	public LogoContext() {
		super();
		getVista().push(getFactory().createSymbolTable(getGlobalSegmentName()));
	}

	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public boolean hasErrors () {
		if (errors == null) return false;
		if (errors.isEmpty()) return false;
		return true;
	}

	public List<String> getErrors () {
		if (errors == null) this.errors = new ArrayList <String> ();
		return errors;
	}

	public void addError (String text) {
		getErrors ().add(text);
	}
	
	public String getAllMessages () {
		StringBuilder tmp = new StringBuilder();
		for (String error : getErrors()) {
			tmp.append(error);
			tmp.append("\n");
		}
		tmp.append(stdout);
		return tmp.toString();
	}

	public SymbolVista getVista () {
		if (vista == null) vista = getFactory ().createSymbolVista(getVistaName ());
		return vista;
	}

	public LogoFactory getFactory () {
		if (factory == null) factory = new LogoFactoryImpl();
		return factory;
	}

	public void addLocalSymbol (Symbol symbol) {
		if (symbol != null) {
			getVista().top().add (symbol);
		}
	}
	
	protected SymbolFunction getInitSegment () {
		if (! Helper.exist (getVista(), getInitSegmentName())) {
			SymbolFunction initSegment = getFactory().createSymbolFunction(getInitSegmentName());
			getVista().top().add (initSegment);
			return initSegment;
		} else {
			List<Symbol> symbols = Helper.get (getVista(), getInitSegmentName());
			return (SymbolFunction) symbols.get(0);
		}
	}

	public void addGlobalInstruction (Node node) {
		SymbolFunction initSegment = getInitSegment ();
		Node bloc = initSegment.getCode();
		if (bloc == null) {
			bloc = getFactory().createNodeBloc();
			initSegment.setCode(bloc);
		}
		if (node != null) {
			bloc.getChildren().add(node);
		}
	}

	static public String getVistaName () {
		return vistaName;
	}

	static public String getInitSegmentName () {
		return initSegmentName;
	}
	
	static public String getGlobalSegmentName () {
		return globalSegmentName;
	}
	
	static final String vistaName = "__vista__";
	static final String globalSegmentName = "__global__";
	static final String initSegmentName = "__init__";

}
