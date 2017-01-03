package com.billhillapps.audiomerge.ui.pages;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import com.billhillapps.audiomerge.ui.AudioMergeUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class Page {


	protected Scene scene;

	protected GridPane rootGrid;

	public Page() {
		scene = new Scene(wrapForScroll(this.createRootPane()), AudioMergeUI.DEFAULT_WIDTH,
				AudioMergeUI.DEFAULT_HEIGHT);
		scene.getStylesheets().add(AudioMergeUI.STYLESHEET);
	}

	protected Pane createRootPane() {
		rootGrid = new GridPane();

		rootGrid.setAlignment(Pos.CENTER);
		rootGrid.setVgap(SPACING);
		rootGrid.setHgap(SPACING);
		rootGrid.setPadding(new Insets(2 * SPACING));

		return rootGrid;
	}

	protected ScrollPane wrapForScroll(Pane rootGrid) {
		ScrollPane scrollPane = new ScrollPane(rootGrid);
		scrollPane.setPadding(new Insets(0));
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}

	public Scene getScene() {
		return this.scene;
	}
}
