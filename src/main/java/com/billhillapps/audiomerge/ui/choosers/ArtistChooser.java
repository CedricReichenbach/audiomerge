package com.billhillapps.audiomerge.ui.choosers;

import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.ui.GridDecisionOption;

import javafx.scene.control.Label;

public class ArtistChooser extends DecisionChooser<Artist> {

	public ArtistChooser() {
		super("Duplicate artist detected",
				"Which one to proceed with? Contents of the other will be merged in, no songs will be lost.");
	}

	@Override
	protected GridDecisionOption buildOption(Artist artist) {
		GridDecisionOption grid = new GridDecisionOption();

		Label label = new Label("Artist named");
		grid.add(label, 0, 0);

		Label name = new Label(artist.getName());
		name.getStyleClass().add("bold");
		grid.add(name, 0, 1);

		return grid;
	}
}
