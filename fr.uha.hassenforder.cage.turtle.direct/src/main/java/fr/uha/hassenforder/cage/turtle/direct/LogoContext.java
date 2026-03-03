package fr.uha.hassenforder.cage.turtle.direct;

import java.util.ArrayList;
import java.util.List;

public class LogoContext {

	private List<String> errors;
	private String stdout;
	
	public LogoContext() {
		super();
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

}
