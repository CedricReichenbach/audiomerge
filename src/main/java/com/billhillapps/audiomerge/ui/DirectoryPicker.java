package com.billhillapps.audiomerge.ui;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirectoryPicker extends HBox {

	private final DirectoryChooser directoryChooser;

	private final TextField pathField;

	private Path chosenPath;
	private boolean pathValid = true;

	public DirectoryPicker(Stage primaryStage) {
		super();

		directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select root directory of music collection");

		pathField = new TextField();
		pathField.setMinWidth(300);
		pathField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				chosenPath = StringUtils.isBlank(newValue) ? null : Paths.get(newValue);
				this.setPathValid(true);
			} catch (InvalidPathException e) {
				this.setPathValid(false);
			}
		});

		Button button = new Button("Select...");
		button.setOnAction(event -> {
			directoryChooser.setInitialDirectory(chosenPath != null ? chosenPath.toFile() : null);
			File chosenFile;
			try {
				chosenFile = directoryChooser.showDialog(primaryStage);
			} catch (IllegalArgumentException e) {
				// chosenPath was not a valid directory path
				directoryChooser.setInitialDirectory(null);
				chosenFile = directoryChooser.showDialog(primaryStage);
			}
			if (chosenFile == null)
				return;

			chosenPath = chosenFile.toPath();
			pathField.setText(chosenPath.toString());
		});

		this.getChildren().add(pathField);
		this.getChildren().add(button);
	}

	private void setPathValid(boolean valid) {
		this.pathValid = valid;
		if (valid)
			pathField.getStyleClass().remove("invalid");
		else {
			chosenPath = null;
			if (!pathField.getStyleClass().contains("invalid"))
				pathField.getStyleClass().add("invalid");
		}
	}

	public boolean isPathValid() {
		return this.pathValid;
	}

	public Path getChosenPath() {
		return chosenPath;
	}
}
