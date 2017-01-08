package com.billhillapps.audiomerge.ui.deciders;

import java.util.concurrent.ExecutionException;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;
import com.billhillapps.audiomerge.ui.DecisionChooser;

public class GuiSongDecider extends MetaDataDistanceSongDecider {

	private final DecisionChooser<Song> chooser;

	public GuiSongDecider(DecisionChooser<Song> chooser) {
		this.chooser = chooser;
	}

	@Override
	public int resolve(Song a, Song b) {
		Song defaultSong = this.betterQuality(a, b) > 0 ? b : a;
		try {
			return chooser.choose(a, b, defaultSong).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Interrupted while waiting for similar Song choice", e);
		}
	}
}
