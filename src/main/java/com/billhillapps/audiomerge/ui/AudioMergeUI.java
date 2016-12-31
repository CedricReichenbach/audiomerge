package com.billhillapps.audiomerge.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 10;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button startButton = new Button("Start");
		startButton.getStyleClass().add("start");
		startButton.setOnAction(event -> {
			// TODO: Start loading, merging...
			System.out.println("Button clicked");
		});

		DirectoryList dirList = new DirectoryList(primaryStage);

		GridPane rootGrid = new GridPane();
		rootGrid.setAlignment(Pos.CENTER);
		rootGrid.setVgap(SPACING);
		rootGrid.setHgap(SPACING);
		
		rootGrid.add(dirList, 0, 0);
		rootGrid.add(startButton, 0, 1);

		Scene scene = new Scene(rootGrid, 600, 400);
		scene.getStylesheets().add(ClassLoader.getSystemResource("application.css").toExternalForm());

		primaryStage.setTitle("AudioMerge");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
