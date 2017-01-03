package com.billhillapps.audiomerge.test;

import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.similarity.deciders.NameDistanceArtistDecider;

public class LexicographicalArtistDecider extends NameDistanceArtistDecider {

	@Override
	public int resolve(Artist a, Artist b) {
		return a.getName().compareTo(b.getName()) * 2 - 1;
	}
}
