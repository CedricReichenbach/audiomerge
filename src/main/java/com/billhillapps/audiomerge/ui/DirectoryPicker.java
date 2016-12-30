package com.billhillapps.audiomerge.ui;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirectoryPicker extends Pane {

	private final DirectoryChooser directoryChooser;

	private Path chosenPath;

	public DirectoryPicker(Stage primaryStage) {
		super();

		HBox container = new HBox();
		this.getChildren().add(container);

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

		container.getChildren().add(pathField);
		container.getChildren().add(button);
	}
}
