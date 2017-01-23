package com.billhillapps.audiomerge.ui.problems;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.problems.CannotReadFileProblem;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CannotReadPrompt extends GridPane {

	private final Consumer<String> directoryOpener;

	private CompletableFuture<Boolean> future;

	public CannotReadPrompt(Consumer<String> directoryOpener) {
		super();

		this.directoryOpener = directoryOpener;

		// if invisible, also remove from layout
		this.managedProperty().bind(this.visibleProperty());
		this.setVisible(false);

		this.setVgap(SPACING / 2);
		this.setHgap(SPACING / 2);

		ColumnConstraints colConstraints = new ColumnConstraints();
		colConstraints.setPercentWidth(50);
		this.getColumnConstraints().addAll(colConstraints, colConstraints);
	}

	public Future<Boolean> promptForSkip(CannotReadFileProblem problem) {
		future = new CompletableFuture<>();

		Platform.runLater(() -> {
			this.setVisible(true);

			String titleText = String.format("Problem while reading audio file: %s",
					problem.getException().getMessage());
			Label title = new Label(titleText);
			title.getStyleClass().add("title");
			this.add(title, 0, 0, 2, 1);

			Label description = new Label(problem.getPath().toString());
			this.add(description, 0, 1, 2, 1);

			Button openDirButton = new Button("Open directory");
			openDirButton.setOnAction(event -> directoryOpener.accept(problem.getPath().getParent().toString()));
			this.add(openDirButton, 0, 2, 2, 1);

			Button abortButton = new Button("Abort merging, stop application");
			abortButton.setOnAction(event -> this.complete(false));
			abortButton.getStyleClass().add("critical");
			this.add(abortButton, 0, 3);

			Button skipButton = new Button("Skip this file and continue");
			skipButton.setOnAction(event -> this.complete(true));
			this.add(skipButton, 1, 3);

			GridPane.setMargin(openDirButton, new Insets(0, 0, SPACING, 0));
			openDirButton.setMaxWidth(Double.MAX_VALUE);
			abortButton.setMaxWidth(Double.MAX_VALUE);
			skipButton.setMaxWidth(Double.MAX_VALUE);
		});

		return future;
	}

	private void complete(boolean answer) {
		future.complete(answer);
		this.setVisible(false);
	}
}
