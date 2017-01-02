package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.pages.StartPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 12;

	public static final double DEFAULT_WIDTH = 600;
	public static final double DEFAULT_HEIGHT = 400;

	private StartPage startPage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		startPage = new StartPage(primaryStage);
		Scene scene = startPage.getScene();
		startPage.onStart(this::showOperationPage);

		primaryStage.setTitle("AudioMerge");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showOperationPage(MergeManager mergeManager) {
		// TODO: Switch page/scene
		// TODO: Start loading, merging...
		mergeManager.addProgressListener((progress, operation) -> {
			// TODO: Show progress bar or something
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
