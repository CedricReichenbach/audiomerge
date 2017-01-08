package com.billhillapps.audiomerge.ui.pages;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.choosers.AlbumChooser;
import com.billhillapps.audiomerge.ui.choosers.ArtistChooser;
import com.billhillapps.audiomerge.ui.choosers.SongChooser;
import com.billhillapps.audiomerge.ui.deciders.GuiAlbumDecider;
import com.billhillapps.audiomerge.ui.deciders.GuiArtistDecider;
import com.billhillapps.audiomerge.ui.deciders.GuiSongDecider;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OperationPage extends Page {

	private final Double CONTENT_WIDTH = 800d;

	private final ArtistChooser artistChooser;
	private final AlbumChooser albumChooser;
	private final SongChooser songChooser;
	private final Label progressLabel;
	private final ProgressBar progressBar;

	private MergeManager mergeManager;

	private final Consumer<MergeManager> onFinishCallback;

	public OperationPage(Consumer<MergeManager> onFinish) {
		super();

		this.onFinishCallback = onFinish;

		rootGrid.setMaxWidth(CONTENT_WIDTH);

		this.artistChooser = new ArtistChooser();
		this.albumChooser = new AlbumChooser();
		this.songChooser = new SongChooser();

		this.progressLabel = new Label();
		progressLabel.getStyleClass().add("progress-label");
		this.progressBar = new ProgressBar(0);
		progressBar.setPrefWidth(CONTENT_WIDTH);
		progressBar.setMaxWidth(Double.MAX_VALUE);
		progressBar.setPadding(new Insets(0, 0, 2 * SPACING, 0));

		VBox progress = new VBox(progressLabel, progressBar);

		rootGrid.add(progress, 0, 0);
		rootGrid.add(artistChooser, 0, 1);
		rootGrid.add(albumChooser, 0, 1);
		rootGrid.add(songChooser, 0, 1);
	}

	// XXX: A bit hacky
	@Override
	protected Pane createRootPane() {
		super.createRootPane();
		// use stack pane for centering
		return new StackPane(rootGrid);
	}

	public void runMergeManager(final MergeManager mergeManager) {
		this.mergeManager = mergeManager;

		mergeManager.setArtistDecider(new GuiArtistDecider(artistChooser));
		mergeManager.setAlbumDecider(new GuiAlbumDecider(albumChooser));
		mergeManager.setSongDecider(new GuiSongDecider(songChooser));

		this.mergeManager.addProgressListener((progress, operation) -> {
			Platform.runLater(() -> {
				progressLabel.setText(operation);
				progressBar.setProgress(progress);
			});
		});

		Thread mergeThread = new Thread(() -> {
			this.mergeManager.execute();

			Platform.runLater(() -> {
				onFinishCallback.accept(this.mergeManager);
			});
		});
		mergeThread.setDaemon(true);
		mergeThread.start();
	}
}
