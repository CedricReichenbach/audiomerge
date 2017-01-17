package com.billhillapps.audiomerge.ui.problems;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CannotReadPrompt extends GridPane {

	private CompletableFuture<Boolean> future;

	public CannotReadPrompt(Consumer<String> directoryOpener) {
		super();

		// if invisible, also remove from layout
		this.managedProperty().bind(this.visibleProperty());
		this.setVisible(false);

		this.setVgap(SPACING / 2);
		this.setHgap(SPACING / 2);
	}

	// TODO: Somehow get path along with exception and add button for opening it
	public Future<Boolean> promptForSkip(CannotReadException e) {
		future = new CompletableFuture<>();

		Platform.runLater(() -> {
			this.setVisible(true);

			String text = String.format("Problem while reading audio file: %s", e.getMessage());
			Label description = new Label(text);
			this.add(description, 0, 0, 2, 1);

			Button abort = new Button("Abort merging, stop application");
			abort.setOnAction(event -> this.complete(false));
			this.add(abort, 0, 1);

			Button skip = new Button("Skip this file and continue");
			skip.setOnAction(event -> this.complete(true));
			this.add(skip, 1, 1);
		});

		return future;
	}

	private void complete(boolean answer) {
		future.complete(answer);
		this.setVisible(false);
	}
}
