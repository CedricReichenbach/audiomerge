package com.billhillapps.audiomerge.ui;

import java.nio.file.Path;
import java.util.Collection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 12;

	private static final double INIT_WIDTH = 600;
	private static final double INIT_HEIGHT = 400;

	private DirectoryList sourceDirList;
	private DirectoryPicker targetDirPicker;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = buildScene(primaryStage);

		primaryStage.setTitle("AudioMerge");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene buildScene(Stage primaryStage) {
		Button startButton = new Button("Start");
		startButton.getStyleClass().add("start");
		startButton.setMaxWidth(Double.MAX_VALUE);
		startButton.setOnAction(event -> startMerging());

		Label dirListLabel = new Label("Source directories");
		dirListLabel.getStyleClass().add("title");

		sourceDirList = new DirectoryList(primaryStage);

		Label targetDirLabel = new Label("Target directory");
		targetDirLabel.getStyleClass().add("title");

		targetDirPicker = new DirectoryPicker(primaryStage);

		GridPane rootGrid = new GridPane();
		rootGrid.setAlignment(Pos.CENTER);
		rootGrid.setVgap(SPACING);
		rootGrid.setHgap(SPACING);
		rootGrid.setPadding(new Insets(2 * SPACING));

		rootGrid.add(dirListLabel, 0, 0);
		rootGrid.add(sourceDirList, 0, 1);
		rootGrid.add(targetDirLabel, 0, 2);
		rootGrid.add(targetDirPicker, 0, 3);
		rootGrid.add(startButton, 0, 4);

		Scene scene = new Scene(wrapForScroll(rootGrid), INIT_WIDTH, INIT_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("application.css").toExternalForm());
		return scene;
	}

	public ScrollPane wrapForScroll(GridPane rootGrid) {
		ScrollPane scrollPane = new ScrollPane(rootGrid);
		scrollPane.setPadding(new Insets(0));
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}

	private void startMerging() {
		if (sourceDirList.hasInvalidPaths()) {
			new Alert(AlertType.ERROR, "There are invalid paths. Please fix them or remove them from the list.").show();
			return;
		}

		Collection<Path> chosenDirs = sourceDirList.getChosenDirs();
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
