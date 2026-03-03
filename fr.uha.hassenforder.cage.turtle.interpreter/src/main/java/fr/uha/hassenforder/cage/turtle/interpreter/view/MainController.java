package fr.uha.hassenforder.cage.turtle.interpreter.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import fr.uha.hassenforder.cage.turtle.interpreter.LogoContext;
import fr.uha.hassenforder.cage.turtle.interpreter.io.IO;
import fr.uha.hassenforder.cage.turtle.interpreter.processor.Processor;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class MainController {

	private boolean allowIncremental = false;
	private Processor processor = new Processor();
	
	private StringProperty status;

	private StringProperty inputContent;
	private StringProperty inputFileName;

	private StringProperty outputContent;

	private ObjectProperty<LogoContext> context;

	@FXML
	private Label statusLabel;

	@FXML
	private TextArea inputTextArea;
	@FXML
	private TextArea outputTextArea;

	@FXML
	private CheckMenuItem localMenuItem;
	@FXML
	private CheckMenuItem remoteMenuItem;

	@FXML
	private CheckMenuItem incrementalMenuItem;
	@FXML
	private CheckMenuItem globalMenuItem;

	@FXML
	private Canvas drawingArea;

    private void updateStatus (String text) {
    	status.set(text);
    }

    private void updateStatus (String prefixe, String suffixe) {
    	StringBuilder tmp = new StringBuilder();
    	if (prefixe != null) {
    		tmp.append(prefixe);
    		tmp.append(" ");
    	}
    	tmp.append("-");
    	if (suffixe != null) {
    		tmp.append(" ");
    		tmp.append(suffixe);
    	}
    	updateStatus(tmp.toString());
    }

    private Window getWindow () {
    	return statusLabel.getScene().getWindow();
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	statusLabel.textProperty().bind(status);

		inputContent = new SimpleStringProperty();
		inputFileName = new SimpleStringProperty();
		inputTextArea.textProperty().bindBidirectional(inputContent);
		inputTextArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				onUpdateInputContent(oldValue, newValue);
			}
    	});
		
		outputContent = new SimpleStringProperty();
		outputTextArea.textProperty().bind(outputContent);

		context = new SimpleObjectProperty<LogoContext>(new LogoContext());
		context.addListener(new ChangeListener<LogoContext>() {
			@Override
			public void changed(ObservableValue<? extends LogoContext> observable, LogoContext oldValue, LogoContext newValue) {
				if (newValue != null) 
					draw ();
			}
		});
		status.set("Ready");
	}

    private void draw () {
		processor.draw(drawingArea);
    }

    @FXML
    private void onQuit() {
    	Platform.exit();
	}

    @FXML
    private void onNew(ActionEvent event) {
		String content = "";
		inputContent.set(content);
    }
    
    @FXML
    private void onDefault(ActionEvent event) {
		try (
			InputStream inputStream = getClass().getResourceAsStream("interpreter.ttl");
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream)))
		{
			StringBuilder tmp = new StringBuilder();
			String line;
			while ((line = inputReader.readLine()) != null) {
				tmp.append(line);
				tmp.append("\n");
			}
			inputContent.set(tmp.toString());
		} catch (IOException e) {
			inputContent.set("Cannot access to the default file");
		}
		inputFileName.set("default.ttl");
    }
    
    @FXML
    private void onLoad(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("TTL Files", "*.ttl")
		);
    	File selectedFile = fileChooser.showOpenDialog(getWindow());
    	if (selectedFile != null) {
    		IO io = new IO();
    		String content = io.load(selectedFile);
    		inputContent.set(content);
    		inputFileName.set(selectedFile.getName());
    	}
	}

    @FXML
    private void onLoadAs() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("TTL Files", "*.ttl")
		);
    	File selectedFile = fileChooser.showOpenDialog(getWindow());
    	if (selectedFile != null) {
    		IO io = new IO();
    		String content = io.load(selectedFile);
    		inputContent.set(content);
    		inputFileName.set(selectedFile.getName());
    	}
    }

    @FXML
    private void onSave() {
	}

    @FXML
    private void onSaveAs() {
	}

    @FXML
    private void onProcess() {
    	updateStatus("Processing", "Started");
		processor.read(new StringReader(inputContent.get()));
		outputContent.set(processor.getContext().getAllMessages());
		this.context.set(processor.getContext());
    	updateStatus("Processing", "Done");
    }

	private void onUpdateInputContent(String oldValue, String newValue) {
		if (allowIncremental) {
	    	updateStatus("Updating", "Started");
	    	updateStatus("Updating", "Done");
		}
	}
	
}
