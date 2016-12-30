package com.billhillapps.audiomerge.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirectoryPicker extends Pane {

	private final DirectoryChooser directoryChooser;

	public DirectoryPicker(Stage primaryStage) {
		super();

		directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select root directory of music collection");

		Button button = new Button("Select...");
		button.setOnAction(event -> {
			directoryChooser.showDialog(primaryStage);
		});

		this.getChildren().add(button);
	}
}
