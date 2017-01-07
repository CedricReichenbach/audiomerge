package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.music.Artist;

import javafx.scene.control.Label;

public class ArtistChooser extends DecisionChooser<Artist> {

	public ArtistChooser() {
		super("Similar artist names",
				"Which one to proceed with? Contents of the other will be merged in, and no songs be lost.");
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
