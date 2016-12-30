package com.billhillapps.audiomerge.test;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;

public class LexigraphicalSongDecider extends MetaDataDistanceSongDecider {

	@Override
	public int resolve(Song a, Song b) {
		return a.getTitle().compareToIgnoreCase(b.getTitle()) * 2 - 1;
	}
}
