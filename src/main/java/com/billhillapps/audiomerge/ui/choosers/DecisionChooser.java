package com.billhillapps.audiomerge.ui.choosers;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.ui.GridDecisionOption;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class DecisionChooser<T> extends GridPane {

	private static final KeyCode OPTION_A_KEY = KeyCode.A;
	private static final KeyCode OPTION_BOTH_KEY = KeyCode.S;
	private static final KeyCode OPTION_B_KEY = KeyCode.D;

	private final Runnable focusTrigger;
	private final Map<KeyCode, Toggle> shortcuts = new HashMap<>();

	private final Button confirmButton;
	private final Separator vLineA, vLineB, vLineBoth;
	private final CheckBox alwaysUseDefaultCheckbox;

	private boolean alwaysUseDefault = false;

	public DecisionChooser(String title, String description, Runnable focusTrigger,
			Consumer<EventHandler<? super KeyEvent>> keyPressedRegistrar) {
		super();

		this.focusTrigger = focusTrigger;

		keyPressedRegistrar.accept(event -> {
			Toggle target = shortcuts.get(event.getCode());
			if (target != null)
				target.setSelected(true);
		});

		// if invisible, also remove from layout
		this.managedProperty().bind(this.visibleProperty());
		this.setVisible(false);

		this.setHgap(SPACING);

		ColumnConstraints colConstraints = new ColumnConstraints();
		colConstraints.setHgrow(Priority.SOMETIMES);
		this.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints);

		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("title");
		this.add(titleLabel, 0, 0, 3, 1);

		Label descriptionLabel = new Label(description);
		descriptionLabel.setWrapText(true);
		this.add(descriptionLabel, 0, 1, 3, 1);

		vLineA = createLine();
		vLineBoth = createLine();
		vLineB = createLine();
		this.add(vLineA, 0, 4);
		this.add(vLineBoth, 1, 4);
		this.add(vLineB, 2, 4);

		this.confirmButton = new Button("Continue");
		confirmButton.setMaxWidth(Double.MAX_VALUE);
		this.add(confirmButton, 0, 5, 3, 1);

		// TODO: Styling and distance
		this.alwaysUseDefaultCheckbox = new CheckBox("Always pick default option, don't ask again");
		alwaysUseDefaultCheckbox.setVisible(false);
		alwaysUseDefaultCheckbox.selectedProperty()
				.addListener((observerable, oldValue, newValue) -> alwaysUseDefault = newValue);
		this.add(alwaysUseDefaultCheckbox, 0, 6, 3, 1);

		giveBottomSpacing(titleLabel, descriptionLabel, confirmButton);

		Node indicatorA = buildShortcutIndicator(OPTION_A_KEY);
		Node indicatorBoth = buildShortcutIndicator(OPTION_BOTH_KEY);
		Node indicatorB = buildShortcutIndicator(OPTION_B_KEY);
		this.addRow(2, indicatorA, indicatorBoth, indicatorB);
	}

	private Separator createLine() {
		Separator vLine = new Separator(Orientation.VERTICAL);
		vLine.getStyleClass().add("choice-line");
		vLine.setMaxWidth(Double.MAX_VALUE);
		vLine.setPrefHeight(2 * SPACING);
		return vLine;
	}

	private void giveBottomSpacing(Node... nodes) {
		for (Node node : nodes)
			GridPane.setMargin(node, new Insets(0, 0, SPACING, 0));
	}

	public Future<Integer> choose(T a, T b, T defaultChoice) {
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();

		Platform.runLater(() -> {
			if (defaultChoice == a | defaultChoice == b) {
				if (alwaysUseDefault) {
					future.complete(0);
					return;
				}

				alwaysUseDefaultCheckbox.setVisible(defaultChoice == a | defaultChoice == b);
			}

			this.setVisible(true);
			this.focusTrigger.run();

			vLineA.setVisible(false);
			vLineBoth.setVisible(false);
			vLineB.setVisible(false);
			confirmButton.setDisable(true);

			GridDecisionOption optionA = buildOption(a);
			GridDecisionOption optionBoth = buildKeepBothOption();
			GridDecisionOption optionB = buildOption(b);

			shortcuts.put(OPTION_A_KEY, optionA);
			shortcuts.put(OPTION_BOTH_KEY, optionBoth);
			shortcuts.put(OPTION_B_KEY, optionB);

			ToggleGroup toggleGroup = makeToggleGroup(optionA, optionBoth, optionB);

			registerCallbacks(optionA, vLineA);
			registerCallbacks(optionBoth, vLineBoth);
			registerCallbacks(optionB, vLineB);

			if (defaultChoice == a)
				optionA.setSelected(true);
			else if (defaultChoice == b)
				optionB.setSelected(true);

			this.addRow(3, optionA, optionBoth, optionB);

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

				this.getChildren().removeAll(optionA, optionBoth, optionB);
				this.setVisible(false);
			});
		});

		return future;
	}

	private void registerCallbacks(GridDecisionOption option, Separator line) {
		option.selectedProperty().addListener((observerable, oldValue, newValue) -> {
			line.setVisible(newValue);

			confirmButton.setDisable(!vLineA.isVisible() & !vLineBoth.isVisible() & !vLineB.isVisible());
		});
	}

	private ToggleGroup makeToggleGroup(GridDecisionOption... options) {
		ToggleGroup toggleGroup = new ToggleGroup();

		for (GridDecisionOption option : options)
			option.setToggleGroup(toggleGroup);

		return toggleGroup;
	}

	protected GridDecisionOption buildKeepBothOption() {
		Label label = new Label("Keep both");
		label.setWrapText(true);

		GridDecisionOption grid = new GridDecisionOption();
		grid.add(label, 0, 0);
		return grid;
	}

	protected Node buildShortcutIndicator(KeyCode key) {
		Label keyLabel = new Label(key.getName());
		keyLabel.getStyleClass().add("shortcut");
		keyLabel.setPadding(new Insets(SPACING / 8, SPACING / 2, SPACING / 8, SPACING / 2));
		GridPane.setHalignment(keyLabel, HPos.CENTER);
		GridPane.setMargin(keyLabel, new Insets(0, 0, SPACING / 2, 0));
		return keyLabel;
	}

	protected abstract GridDecisionOption buildOption(T item);
}
