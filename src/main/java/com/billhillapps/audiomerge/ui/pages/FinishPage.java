package com.billhillapps.audiomerge.ui.pages;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;
import static java.lang.String.format;

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

		StringBuilder text = new StringBuilder();

		Collection<Integer> sourceSizes = statistics.getSourceCollectionSizes();
		text.append(format("%s collection%s containing ", sourceSizes.size(), sourceSizes.size() == 1 ? "" : "s"));
		text.append(String.join(" + ", sourceSizes.stream().map(i -> i.toString()).collect(Collectors.toList())));
		if (sourceSizes.size() == 0)
			text.append("0");
		if (sourceSizes.size() > 1) {
			text.append(" = ");
			text.append(sourceSizes.stream().mapToInt(Integer::intValue).sum());
		}
		text.append(" songs have been merged, resulting in one collection containing ");
		text.append(statistics.getResultCollectionSize());
		text.append(" songs.\n");

		text.append(
				format("A total of %s duplicate songs were merged, among which %s were detected similarities (not exact matches).\n",
						statistics.getTotalSongsMerged(), statistics.getSimilarSongsMerged()));
		text.append(format("%s artist and %s album similarities were detected and merged.",
				statistics.getSimilarArtistsMerged(), statistics.getSimilarAlbumsMerged()));

		Label label = new Label(text.toString());
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
