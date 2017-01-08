package com.billhillapps.audiomerge.ui.pages;

import java.util.function.Consumer;

import com.billhillapps.audiomerge.processing.MergeManager;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FinishPage extends Page {

	private final Consumer<String> directoryOpener;
	private final Button openDirectoryButton;

	public FinishPage(Consumer<String> directoryOpener) {
		super();
		
		// TODO: Collect and show statistics here

		this.directoryOpener = directoryOpener;

		Label title = new Label("Merging finished successfully!");
		title.getStyleClass().add("title");
		rootGrid.add(title, 0, 0);

		openDirectoryButton = new Button("Open target directory");
		GridPane.setHalignment(openDirectoryButton, HPos.CENTER);
		rootGrid.add(openDirectoryButton, 0, 1);
	}

	public void showFinish(MergeManager mergeManager) {
		openDirectoryButton.setOnAction(event -> {
			directoryOpener.accept(mergeManager.getDestination().toString());
		});
	}
}
