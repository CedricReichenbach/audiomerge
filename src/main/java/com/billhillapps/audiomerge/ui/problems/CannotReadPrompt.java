package com.billhillapps.audiomerge.ui.problems;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.problems.CannotReadFileProblem;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	}

	public Future<Boolean> promptForSkip(CannotReadFileProblem problem) {
		future = new CompletableFuture<>();

		Platform.runLater(() -> {
			this.setVisible(true);

			// TODO: Differentiate path (and maybe message) from text, e.g.
			// through mono or italic font
			String text = String.format("Problem while reading audio file: %s\nPath: %s",
					problem.getException().getMessage(), problem.getPath());
			Label description = new Label(text);
			this.add(description, 0, 0, 2, 1);

			// TODO: Stretch buttons to full (or half) width
			Button openDirButton = new Button("Open directory");
			openDirButton.setOnAction(event -> directoryOpener.accept(problem.getPath().getParent().toString()));
			this.add(openDirButton, 0, 1, 2, 1);

			Button abort = new Button("Abort merging, stop application");
			abort.setOnAction(event -> this.complete(false));
			this.add(abort, 0, 2);

			Button skip = new Button("Skip this file and continue");
			skip.setOnAction(event -> this.complete(true));
			this.add(skip, 1, 2);
		});

		return future;
	}

	private void complete(boolean answer) {
		future.complete(answer);
		this.setVisible(false);
	}
}
