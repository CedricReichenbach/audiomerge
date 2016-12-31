package com.billhillapps.audiomerge.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectoryList extends VBox {

	public DirectoryList(Stage primaryStage) {
		super();

		this.setSpacing(AudioMergeUI.SPACING / 2);

		this.getChildren().add(new DirectoryPicker(primaryStage));

		Button addButton = new Button("+");
		addButton.getStyleClass().add("dynamic");
		addButton.setOnAction(addButtonEvent -> {
			ObservableList<Node> children = this.getChildren();

			HBox row = new HBox();
			row.setSpacing(AudioMergeUI.SPACING / 2);
			children.add(children.size() - 1, row);

			Button removeButton = new Button("−");
			removeButton.getStyleClass().add("dynamic");
			removeButton.setOnAction(removeButtonEvent -> {
				this.getChildren().remove(row);
			});

			row.getChildren().add(new DirectoryPicker(primaryStage));
			row.getChildren().add(removeButton);
		});
		this.getChildren().add(addButton);
	}
}
