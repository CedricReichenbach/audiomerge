package com.billhillapps.audiomerge.similarity.deciders;

import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.similarity.Decider;

public class NameDistanceArtistDecider implements Decider<Artist> {

	@Override
	public boolean areSimilar(Artist a, Artist b) {
		return DistanceUtil.areSimilar(a.getName(), b.getName());
	}

	@Override
	public int resolve(Artist a, Artist b) {
		return DeciderUtil.resolveUsingStdIn(a, b);
	}
}
