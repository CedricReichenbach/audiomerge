package com.billhillapps.audiomerge.ui;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class DecisionChooser<T> extends GridPane {

	public Future<Integer> choose(T a, T b, T defaultChoice) {
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();

		Platform.runLater(() -> {
			Node optionA = buildOption(a);
			Node optionBoth = buildKeepBothOption();
			Node optionB = buildOption(b);

			this.add(optionA, 0, 0);
			this.add(optionBoth, 1, 0);
			this.add(optionB, 2, 0);
			// TODO: Sophisticate, e.g. confirm button (select -> confirm),
			// default etc.

			// TODO future.complete(result) when e.g. clicked
		});

		return future;
	}

	protected Node buildKeepBothOption() {
		return new Label("Keep both");
	}

	protected abstract Node buildOption(T item);
}
