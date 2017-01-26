package com.billhillapps.audiomerge.ui.problems;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.problems.AudioLoadingProblem;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CannotReadPrompt extends GridPane {

	private final Consumer<String> directoryOpener;
	private final Runnable focusTrigger;

	private CompletableFuture<Boolean> future;

	private boolean alwaysSkip = false;

	private final Label titleLabel = new Label();
	private final Label descriptionLabel = new Label();
	private final Button openDirButton = new Button("Open directory");

	public CannotReadPrompt(Consumer<String> directoryOpener, Runnable focusTrigger) {
		super();

		this.directoryOpener = directoryOpener;
		this.focusTrigger = focusTrigger;

		// if invisible, also remove from layout
		this.managedProperty().bind(this.visibleProperty());
		this.setVisible(false);

		this.setVgap(SPACING / 2);
		this.setHgap(SPACING / 2);

		ColumnConstraints colConstraints = new ColumnConstraints();
		colConstraints.setPercentWidth(50);
		this.getColumnConstraints().addAll(colConstraints, colConstraints);

		initUI();
	}

	public Future<Boolean> promptForSkip(AudioLoadingProblem problem) {
		future = new CompletableFuture<>();

		Platform.runLater(() -> {
			if (alwaysSkip) {
				future.complete(true);
				return;
			}

			updateUI(problem);
			focusTrigger.run();
		});

		return future;
	}

	private void initUI() {
		titleLabel.getStyleClass().add("title");
		titleLabel.setWrapText(true);
		this.add(titleLabel, 0, 0, 2, 1);

		descriptionLabel.setWrapText(true);
		this.add(descriptionLabel, 0, 1, 2, 1);

		this.add(openDirButton, 0, 2, 2, 1);

		Button abortButton = new Button("Abort merging, stop application");
		abortButton.setOnAction(event -> this.complete(false));
		abortButton.getStyleClass().add("critical");
		this.add(abortButton, 0, 3);

		Button skipButton = new Button("Skip this file and continue");
		skipButton.setOnAction(event -> this.complete(true));
		this.add(skipButton, 1, 3);

		CheckBox alwaysSkipCheckbox = new CheckBox("Always skip on reading errors");
		alwaysSkipCheckbox.selectedProperty()
				.addListener((observable, oldValue, newValue) -> this.alwaysSkip = newValue);
		this.add(alwaysSkipCheckbox, 1, 4);

		GridPane.setMargin(openDirButton, new Insets(0, 0, SPACING, 0));
		openDirButton.setMaxWidth(Double.MAX_VALUE);
		abortButton.setMaxWidth(Double.MAX_VALUE);
		skipButton.setMaxWidth(Double.MAX_VALUE);
	}

	private void updateUI(AudioLoadingProblem problem) {
		String titleText = String.format("Problem while reading audio file: %s", problem.getException().getMessage());
		titleLabel.setText(titleText);

		descriptionLabel.setText(problem.getPath().toString());

		openDirButton.setOnAction(event -> directoryOpener.accept(problem.getPath().getParent().toString()));

		this.setVisible(true);
	}

	private void complete(boolean answer) {
		future.complete(answer);
		this.setVisible(false);
	}
}
