package com.billhillapps.audiomerge.ui;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirectoryPicker extends HBox {

	private final DirectoryChooser directoryChooser;

	private Path chosenPath;

	public DirectoryPicker(Stage primaryStage) {
		super();

		directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select root directory of music collection");

		TextField pathField = new TextField();

		Button button = new Button("Select...");
		button.setOnAction(event -> {
			File chosenFile = directoryChooser.showDialog(primaryStage);
			if (chosenFile == null)
				return;

			chosenPath = chosenFile.toPath();
			pathField.setText(chosenPath.toString());
		});

		this.getChildren().add(pathField);
		this.getChildren().add(button);
	}
}
