package com.billhillapps.audiomerge.ui;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public abstract class DecisionChooser<T> extends GridPane {

	public DecisionChooser() {
		this.setVgap(SPACING);
		this.setHgap(SPACING);
	}

	public Future<Integer> choose(T a, T b, T defaultChoice) {
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();

		Platform.runLater(() -> {
			// FIXME: Not working (i.e. multiple can be selected)
			ToggleGroup toggleGroup = new ToggleGroup();

			GridDecisionOption optionA = buildOption(a);
			GridDecisionOption optionBoth = buildKeepBothOption();
			GridDecisionOption optionB = buildOption(b);

			optionA.setToggleGroup(toggleGroup);
			optionBoth.setToggleGroup(toggleGroup);
			optionB.setToggleGroup(toggleGroup);

			this.add(optionA, 0, 0);
			this.add(optionBoth, 1, 0);
			this.add(optionB, 2, 0);
			// TODO: Sophisticate, e.g. confirm button (select -> confirm),
			// default etc.

			// TODO future.complete(result) when e.g. clicked
		});

		return future;
	}

	protected GridDecisionOption buildKeepBothOption() {
		Label label = new Label("Keep both");
		GridDecisionOption grid = new GridDecisionOption();
		grid.add(label, 0, 0);
		return grid;
	}

	protected abstract GridDecisionOption buildOption(T item);
}
