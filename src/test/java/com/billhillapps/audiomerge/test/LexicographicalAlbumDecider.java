package com.billhillapps.audiomerge.test;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.similarity.deciders.TitleDistanceAlbumDecider;

public class LexicographicalAlbumDecider extends TitleDistanceAlbumDecider {

	@Override
	public int resolve(Album a, Album b) {
		return a.getAlbumTitle().compareTo(b.getAlbumTitle()) * 2 - 1;
	}
}
