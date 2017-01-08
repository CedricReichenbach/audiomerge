package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.music.Song;

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

		addToGrid(optionGrid, "Title", song.getTitle(), 1);
		addToGrid(optionGrid, "Bit rate", song.getBitRate() + " kbit/s", 2);
		// TODO: Maybe show if variable bit rate
		addToGrid(optionGrid, "Artist", song.getArtistName(), 3);
		addToGrid(optionGrid, "Album", song.getAlbumTitle(), 4);

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
