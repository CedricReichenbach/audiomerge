package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.music.Artist;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class ArtistChooser extends DecisionChooser<Artist> {

	@Override
	protected Node buildOption(Artist album) {
		return new Label(album.getName());
	}
}
