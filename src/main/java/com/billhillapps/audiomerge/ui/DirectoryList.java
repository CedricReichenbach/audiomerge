package com.billhillapps.audiomerge.ui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectoryList extends VBox {

	Collection<DirectoryPicker> pickers = new ArrayList<>();

	public DirectoryList(Stage primaryStage) {
		super();

		this.setSpacing(AudioMergeUI.SPACING / 2);

		DirectoryPicker firstPicker = new DirectoryPicker(primaryStage);
		pickers.add(firstPicker);
		this.getChildren().add(firstPicker);

		Button addButton = new Button("+");
		addButton.getStyleClass().add("dynamic");
		addButton.setOnAction(addButtonEvent -> {
			ObservableList<Node> children = this.getChildren();

			HBox row = new HBox();
			row.setSpacing(AudioMergeUI.SPACING / 2);
			children.add(children.size() - 1, row);

			DirectoryPicker picker = new DirectoryPicker(primaryStage);
			pickers.add(picker);

			Button removeButton = new Button("−");
			removeButton.getStyleClass().add("dynamic");
			removeButton.setOnAction(removeButtonEvent -> {
				pickers.remove(picker);
				this.getChildren().remove(row);
			});

			row.getChildren().add(picker);
			row.getChildren().add(removeButton);
		});
		this.getChildren().add(addButton);
	}

	public Collection<Path> getChosenDirs() {
		return pickers.stream().map(DirectoryPicker::getChosenPath).filter(path -> path != null)
				.collect(Collectors.toList());
	}

	public boolean hasInvalidPaths() {
		return !pickers.stream().allMatch(DirectoryPicker::isPathValid);
	}
}
