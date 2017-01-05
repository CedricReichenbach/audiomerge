package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.music.Artist;

import javafx.scene.control.Label;

public class ArtistChooser extends DecisionChooser<Artist> {

	@Override
	protected GridDecisionOption buildOption(Artist artist) {
		GridDecisionOption grid = new GridDecisionOption();

		Label label = new Label(artist.getName());
		grid.add(label, 0, 0);

		return grid;
	}
}
