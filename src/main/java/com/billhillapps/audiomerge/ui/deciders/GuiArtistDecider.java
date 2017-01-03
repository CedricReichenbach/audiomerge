package com.billhillapps.audiomerge.ui.deciders;

import java.util.concurrent.ExecutionException;

import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.similarity.deciders.NameDistanceArtistDecider;
import com.billhillapps.audiomerge.ui.DecisionChooser;

public class GuiArtistDecider extends NameDistanceArtistDecider {

	final DecisionChooser<Artist> chooser;

	public GuiArtistDecider(DecisionChooser<Artist> chooser) {
		this.chooser = chooser;
	}

	@Override
	public int resolve(Artist a, Artist b) {
		try {
			return chooser.choose(a, b, null).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO: Better handling than Holzhammermethode?
			throw new RuntimeException("Was interrupted waiting for artist decision", e);
		}
	}
}
