package fr.uha.hassenforder.cage.xon;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONObject;

import fr.uha.hassenforder.cage.xon.dump.XonDumper;
import fr.uha.hassenforder.cage.xon.model.xon.Node;
import fr.uha.hassenforder.cage.xon.reader.XonReader;
import fr.uha.hassenforder.cage.xon.transformer.Xon2Json;

public class Main {

	private File validateInputFile (String filename) {
		try {
			File file = new File (filename);
			System.out.println("Current input file: " + file.getAbsolutePath());
			if (! file.exists()) return null;
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private File validateOutputFile (String filename) {
		try {
			File file = new File (filename);
			File parent = file.getParentFile();
			parent.mkdirs();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Node read (String name) {
		try {
			File inputFile = validateInputFile(name);
			if (inputFile == null) return null;
			FileReader fileReader = new FileReader(inputFile);
			XonReader reader = new XonReader();
			Node root = reader.read(fileReader);
			return root;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void dump(String outputfileName, Node root) {
		try {
			File outputFile = validateOutputFile(outputfileName);
			if (outputFile == null) return;
			FileWriter fileWriter = new FileWriter(outputFile);
			XonDumper writer = new XonDumper(fileWriter);
			writer.write(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void write(String name, Node root) {
		try {
			File outputFile = validateOutputFile(name);
			if (outputFile == null) return;
			Xon2Json transformer = new Xon2Json();
			JSONObject json = transformer.transform(root);
			FileWriter fileWriter = new FileWriter(outputFile);
			json.write(fileWriter, 3, 0);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process(String name) {
		Node root = read(name);
		if (root == null) return;
//		dump(name + ".dump", root);
		write(name + ".out", root);
	}

	static public void main(String argv[]) {    
		try {
			String name;
			if (argv.length == 1) {
				name = argv[0];
			} else {
				name = "cage.turtle/fr.uha.hassenforder.cage.xon/test.txt";
			}
			Main main = new Main();
			File file = new File(".");
			System.out.println("Current directory: " + file.getAbsolutePath());
			main.process(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


