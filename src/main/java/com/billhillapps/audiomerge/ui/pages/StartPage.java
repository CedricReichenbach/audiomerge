package com.billhillapps.audiomerge.ui.pages;

import java.nio.file.Path;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.DirectoryList;
import com.billhillapps.audiomerge.ui.DirectoryPicker;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartPage extends Page {

	private DirectoryList sourceDirList;
	private DirectoryPicker targetDirPicker;

	private Consumer<MergeManager> onStartCallback;

	public StartPage(Stage primaryStage) {
		super();

		Label dirListLabel = new Label("Source directories");
		dirListLabel.getStyleClass().add("title");

		sourceDirList = new DirectoryList(primaryStage);

		Label targetDirLabel = new Label("Target directory");
		targetDirLabel.getStyleClass().add("title");

		targetDirPicker = new DirectoryPicker(primaryStage);

		Button startButton = new Button("Start");
		startButton.getStyleClass().add("start");
		startButton.setMaxWidth(Double.MAX_VALUE);
		startButton.setOnAction(event -> startMerging());

		rootGrid.add(dirListLabel, 0, 0);
		rootGrid.add(sourceDirList, 0, 1);
		rootGrid.add(targetDirLabel, 0, 2);
		rootGrid.add(targetDirPicker, 0, 3);
		rootGrid.add(startButton, 0, 4);
	}

	private void startMerging() {
		if (sourceDirList.hasInvalidPaths()) {
			new Alert(AlertType.ERROR, "There are invalid source paths. Please fix them or remove them from the list.")
					.show();
			return;
		}

		final Path[] sourcePaths = sourceDirList.getChosenDirs().toArray(new Path[] {});
		if (sourcePaths.length == 0) {
			new Alert(AlertType.INFORMATION, "No directory selected, please add at least one.").show();
			return;
		}

		if (!targetDirPicker.isPathValid()) {
			new Alert(AlertType.ERROR, "Target path is invalid. Please adjust it and try again.").show();
			return;
		}

		final Path targetPath = targetDirPicker.getChosenPath();
		if (targetPath == null) {
			new Alert(AlertType.INFORMATION, "No target directory set, please select one.").show();
			return;
		}
		if (targetPath.toFile().list().length > 0) {
			new Alert(AlertType.WARNING, "Target directory contains folders or files, please select an empty one.")
					.show();
			return;
		}

		// TODO: Use custom, GUI-based deciders
		MergeManager mergeManager = new MergeManager(targetPath, sourcePaths);
		onStartCallback.accept(mergeManager);
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

	public void onStart(Consumer<MergeManager> onStartCallback) {
		this.onStartCallback = onStartCallback;
	}

}
