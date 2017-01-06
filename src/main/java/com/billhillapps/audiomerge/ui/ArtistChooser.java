package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.music.Artist;

import javafx.scene.control.Label;

public class ArtistChooser extends DecisionChooser<Artist> {

	public ArtistChooser() {
		super("Possibly same artist - which one to proceed with? Contents of the other will be merged in, and no songs be lost.");
	}

	@Override
	protected GridDecisionOption buildOption(Artist artist) {
		GridDecisionOption grid = new GridDecisionOption();

		Label label = new Label(artist.getName());
		grid.add(label, 0, 0);

		return grid;
	}
}
