package com.billhillapps.audiomerge.ui.deciders;

import java.util.concurrent.ExecutionException;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.similarity.deciders.TitleDistanceAlbumDecider;
import com.billhillapps.audiomerge.ui.DecisionChooser;

public class GuiAlbumDecider extends TitleDistanceAlbumDecider {

	final DecisionChooser<Album> chooser;

	public GuiAlbumDecider(DecisionChooser<Album> chooser) {
		super();

		this.chooser = chooser;
	}

	@Override
	public int resolve(Album a, Album b) {
		try {
			return chooser.choose(a, b, null).get();
		} catch (InterruptedException | ExecutionException e) {
			// XXX: Better handling?
			throw new RuntimeException("Interrupted when waiting for album decision", e);
		}
	}
}
