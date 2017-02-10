package com.billhillapps.audiomerge.ui.choosers;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.nio.file.Path;
import java.util.function.Consumer;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.ui.AudioPlayer;
import com.billhillapps.audiomerge.ui.GridDecisionOption;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class SongChooser extends DecisionChooser<Song> {

	private final Consumer<String> directoryOpener;

	public SongChooser(Consumer<String> directoryOpener, Runnable focusTrigger,
			Consumer<EventHandler<? super KeyEvent>> keyPressedRegistrar) {
		super("Duplicate song detected", "Which one to proceed with? The other one will be deleted.", focusTrigger,
				keyPressedRegistrar);

		this.directoryOpener = directoryOpener;
	}

	@Override
	protected GridDecisionOption buildOption(final Song song) {
		GridDecisionOption optionGrid = new GridDecisionOption();

		Label label = new Label("Song");
		label.getStyleClass().add("italic");
		optionGrid.add(label, 0, 0, 2, 1);
		GridPane.setMargin(label, new Insets(0, 0, SPACING / 2, 0));

		addToGrid(optionGrid, "Title", song.getTitle(), 1);
		String bitRate = song.getBitRate() + " kbit/s";
		if (song.isVariableBitRate())
			bitRate += " (variable)";
		addToGrid(optionGrid, "Bit rate", bitRate, 2);
		addToGrid(optionGrid, "Artist", song.getArtistName(), 3);
		addToGrid(optionGrid, "Album", song.getAlbumTitle(), 4);

		Path songPath = song.getPath();
		if (songPath != null) {
			Button openDir = new Button("Open directory");
			openDir.setOnAction(event -> directoryOpener.accept(songPath.getParent().toString()));
			optionGrid.add(openDir, 0, 5, 2, 1);
			centerAndPad(openDir);

			AudioPlayer player = new AudioPlayer(songPath);
			optionGrid.add(player, 0, 6, 2, 1);
			centerAndPad(player);
		}

		return optionGrid;
	}

	private void centerAndPad(Node node) {
		GridPane.setMargin(node, new Insets(SPACING / 2, 0, 0, 0));
		GridPane.setHalignment(node, HPos.CENTER);
	}

	private void addToGrid(final GridPane grid, final String key, final String value, final int row) {
		grid.add(new Label(key + ":"), 0, row);

		Label valueLabel = new Label(value);
		valueLabel.getStyleClass().add("bold");
		valueLabel.setWrapText(true);
		grid.add(valueLabel, 1, row);
	}
}
