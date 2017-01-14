package com.billhillapps.audiomerge.ui.choosers;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import java.nio.file.Path;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.ui.AudioPlayer;
import com.billhillapps.audiomerge.ui.GridDecisionOption;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SongChooser extends DecisionChooser<Song> {

	public SongChooser() {
		super("Duplicate song detected", "Which one to proceed with? The other one will be deleted.");
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
			AudioPlayer player = new AudioPlayer(songPath);
			optionGrid.add(player, 0, 5, 2, 1);
			GridPane.setMargin(player, new Insets(SPACING / 2, 0, 0, 0));
			GridPane.setHalignment(player, HPos.CENTER);
		}

		return optionGrid;
	}

	private void addToGrid(final GridPane grid, final String key, final String value, final int row) {
		grid.add(new Label(key + ":"), 0, row);

		Label valueLabel = new Label(value);
		valueLabel.getStyleClass().add("bold");
		valueLabel.setWrapText(true);
		grid.add(valueLabel, 1, row);
	}
}
