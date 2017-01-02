package com.billhillapps.audiomerge.ui.pages;

import com.billhillapps.audiomerge.processing.MergeManager;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class OperationPage extends Page {

	private final Label progressLabel;
	private final ProgressBar progressBar;

	private MergeManager mergeManager;

	public OperationPage(Stage primaryStage) {
		super();

		this.progressLabel = new Label();
		this.progressBar = new ProgressBar(0);

		rootGrid.add(progressLabel, 0, 0);
		rootGrid.add(progressBar, 0, 1);
	}

	public void runMergeManager(MergeManager mergeManager) {
		this.mergeManager = mergeManager;

		this.mergeManager.addProgressListener((progress, operation) -> {
			progressLabel.setText(operation);
			progressBar.setProgress(progress);
		});

		this.mergeManager.execute();
	}
}
