package com.billhillapps.audiomerge.ui.pages;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.ArtistChooser;
import com.billhillapps.audiomerge.ui.deciders.GuiArtistDecider;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OperationPage extends Page {

	private final ArtistChooser artistChooser;
	private final Label progressLabel;
	private final ProgressBar progressBar;

	private MergeManager mergeManager;

	public OperationPage(Stage primaryStage) {
		super();

		this.artistChooser = new ArtistChooser();

		this.progressLabel = new Label();
		this.progressBar = new ProgressBar(0);
		progressBar.setMaxWidth(Double.MAX_VALUE * 2);

		VBox progress = new VBox(progressLabel, progressBar);

		rootGrid.add(progress, 0, 0);
		rootGrid.add(artistChooser, 0, 1);
	}

	public void runMergeManager(final MergeManager mergeManager) {
		this.mergeManager = mergeManager;

		mergeManager.setArtistDecider(new GuiArtistDecider(artistChooser));
		// TODO: Use custom, GUI-based deciders for Album and Song

		this.mergeManager.addProgressListener((progress, operation) -> {
			Platform.runLater(() -> {
				progressLabel.setText(operation);
				progressBar.setProgress(progress);
			});
		});

		new Thread(() -> {
			this.mergeManager.execute();
			// TODO: Join when finished or something
		}).start();
	}
}
