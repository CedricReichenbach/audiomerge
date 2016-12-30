package com.billhillapps.audiomerge.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button startButton = new Button("Start");
		startButton.setOnAction(event -> {
			// TODO: Start loading, merging...
			System.out.println("Button clicked");
		});

		VBox dirsBox = new VBox();
		dirsBox.getChildren().add(new DirectoryPicker(primaryStage));

		StackPane root = new StackPane();
		root.getChildren().add(startButton);
		root.getChildren().add(dirsBox);

		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(ClassLoader.getSystemResource("application.css").toExternalForm());

		primaryStage.setTitle("AudioMerge");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
