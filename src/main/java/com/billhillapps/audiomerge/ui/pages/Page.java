package com.billhillapps.audiomerge.ui.pages;

import com.billhillapps.audiomerge.ui.AudioMergeUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public abstract class Page {

	protected Scene scene;

	public Page() {
		scene = new Scene(wrapForScroll(this.createRootPane()), AudioMergeUI.DEFAULT_WIDTH,
				AudioMergeUI.DEFAULT_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("application.css").toExternalForm());
	}

	protected abstract Pane createRootPane();

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
