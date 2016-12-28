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
		// TODO: Implement
		System.out.println(String.format("Trying to resolve: '%s' vs '%s'", a, b));
		return -1;
	}

}
