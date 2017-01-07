package com.billhillapps.audiomerge.ui;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public abstract class DecisionChooser<T> extends GridPane {

	private final Button confirmButton;

	public DecisionChooser(String title, String description) {
		super();

		this.setVgap(SPACING);
		this.setHgap(SPACING);

		ColumnConstraints colConstraints = new ColumnConstraints();
		colConstraints.setPercentWidth(100d / 3);
		this.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints);

		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("title");
		this.add(titleLabel, 0, 0, 3, 1);

		Label descriptionLabel = new Label(description);
		descriptionLabel.setWrapText(true);
		this.add(descriptionLabel, 0, 1, 3, 1);

		this.confirmButton = new Button("Continue");
		confirmButton.setMaxWidth(Double.MAX_VALUE);
		this.add(confirmButton, 0, 3, 3, 1);
	}

	public Future<Integer> choose(T a, T b, T defaultChoice) {
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();

		Platform.runLater(() -> {
			GridDecisionOption optionA = buildOption(a);
			GridDecisionOption optionBoth = buildKeepBothOption();
			GridDecisionOption optionB = buildOption(b);

			ToggleGroup toggleGroup = makeToggleGroup(optionA, optionBoth, optionB);

			if (defaultChoice == a)
				optionA.setSelected(true);
			else if (defaultChoice == b)
				optionB.setSelected(true);

			this.add(optionA, 0, 2);
			this.add(optionBoth, 1, 2);
			this.add(optionB, 2, 2);

			confirmButton.setOnAction(event -> {
				Toggle selected = toggleGroup.getSelectedToggle();
				if (selected == null)
					return;

				if (selected == optionA)
					future.complete(-1);
				if (selected == optionBoth)
					future.complete(0);
				else if (selected == optionB)
					future.complete(1);
			});
		});

		return future;
	}

	private ToggleGroup makeToggleGroup(GridDecisionOption... options) {
		ToggleGroup toggleGroup = new ToggleGroup();

		for (GridDecisionOption option : options)
			option.setToggleGroup(toggleGroup);

		return toggleGroup;
	}

	protected GridDecisionOption buildKeepBothOption() {
		Label label = new Label("Keep both");
		
		GridDecisionOption grid = new GridDecisionOption();
		grid.add(label, 0, 0);
		return grid;
	}

	protected abstract GridDecisionOption buildOption(T item);
}
