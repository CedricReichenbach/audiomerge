package com.billhillapps.audiomerge.ui;

import java.nio.file.Path;
import java.util.Collection;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 10;

	private DirectoryList dirList;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button startButton = new Button("Start");
		startButton.getStyleClass().add("start");
		startButton.setOnAction(event -> startMerging());

		Label dirListLabel = new Label("Directories");
		dirListLabel.getStyleClass().add("title");

		dirList = new DirectoryList(primaryStage);

		GridPane rootGrid = new GridPane();
		rootGrid.setAlignment(Pos.CENTER);
		rootGrid.setVgap(SPACING);
		rootGrid.setHgap(SPACING);

		rootGrid.add(dirListLabel, 0, 0);
		rootGrid.add(dirList, 0, 1);
		rootGrid.add(startButton, 0, 2);

		Scene scene = new Scene(rootGrid, 600, 400);
		scene.getStylesheets().add(ClassLoader.getSystemResource("application.css").toExternalForm());

		primaryStage.setTitle("AudioMerge");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void startMerging() {
		if (dirList.hasInvalidPaths()) {
			new Alert(AlertType.ERROR, "There are invalid paths. Please fix them or remove them from the list.").show();
			return;
		}
		
		Collection<Path> chosenDirs = dirList.getChosenDirs();
		if (chosenDirs.size() == 0) {
			new Alert(AlertType.INFORMATION, "No directory selected, please add at least one.").show();
			return;
		}

		// TODO: Start loading, merging...
		System.out.println("Button clicked");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
