package com.billhillapps.audiomerge.ui.pages;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.AudioMergeUI;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OperationPage implements Page {

	private final Scene scene;
	private final ProgressBar progressBar;
	private final GridPane rootGrid;

	public OperationPage(Stage primaryStage, MergeManager mergeManager) {
		this.progressBar = new ProgressBar();
		// TODO
		
		this.rootGrid = new GridPane();
		rootGrid.add(progressBar, 0, 0);
		
		scene = new Scene(rootGrid, AudioMergeUI.DEFAULT_WIDTH, AudioMergeUI.DEFAULT_HEIGHT);
	}

	@Override
	public Scene getScene() {
		return scene;
	}

}
