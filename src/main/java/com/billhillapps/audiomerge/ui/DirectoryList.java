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

		Button addButton = new Button("Add directory");
		addButton.setOnAction(event -> {
			ObservableList<Node> children = this.getChildren();

			HBox row = new HBox();
			row.setSpacing(AudioMergeUI.SPACING / 2);
			children.add(children.size() - 1, row);

			row.getChildren().add(new DirectoryPicker(primaryStage));
			row.getChildren().add(new Button("-"));
			// TODO: Remove row on button click
		});
		this.getChildren().add(addButton);
	}
}
