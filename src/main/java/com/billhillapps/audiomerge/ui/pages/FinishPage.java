package com.billhillapps.audiomerge.ui.pages;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.processing.Statistics;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FinishPage extends Page {

	private final Consumer<String> directoryOpener;
	private final Button openDirectoryButton;

	public FinishPage(Consumer<String> directoryOpener) {
		super();

		this.directoryOpener = directoryOpener;

		rootGrid.setVgap(SPACING);

		Label title = new Label("Merging finished successfully!");
		title.getStyleClass().add("title");
		rootGrid.add(title, 0, 0);

		openDirectoryButton = new Button("Open target directory");
		GridPane.setHalignment(openDirectoryButton, HPos.CENTER);
		rootGrid.add(openDirectoryButton, 0, 2);
	}

	private Node buildStatistics() {
		Statistics statistics = Statistics.getInstance();

		StringBuilder statsText = new StringBuilder();

		Collection<Integer> sourceSizes = statistics.getSourceCollectionSizes();
		statsText.append(String.format("%1$s collection%2$s of size%2$s ", sourceSizes.size(),
				sourceSizes.size() == 1 ? "" : "s"));
		statsText.append(String.join(" / ", sourceSizes.stream().map(i -> i.toString()).collect(Collectors.toList())));
		statsText.append(" (number of songs) have been merged, resulting in one collection of size ");
		statsText.append(statistics.getResultCollectionSize());
		statsText.append(".");

		Label label = new Label(statsText.toString());
		label.setWrapText(true);
		return label;
	}

	public void showFinish(MergeManager mergeManager) {
		rootGrid.add(buildStatistics(), 0, 1);

		openDirectoryButton.setOnAction(event -> {
			directoryOpener.accept(mergeManager.getDestination().toString());
		});
	}
}
