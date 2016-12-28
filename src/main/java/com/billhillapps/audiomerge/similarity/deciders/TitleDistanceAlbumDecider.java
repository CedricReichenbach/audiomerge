package com.billhillapps.audiomerge.similarity.deciders;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.similarity.Decider;

public class TitleDistanceAlbumDecider implements Decider<Album> {

	@Override
	public boolean areSimilar(Album a, Album b) {
		return DistanceUtil.areSimilar(a.getAlbumTitle(), b.getAlbumTitle());
	}

	@Override
	public int resolve(Album a, Album b) {
		// TODO: Implement
		System.out.println(String.format("Trying to resolve: '%s' vs '%s'", a, b));
		return -1;
	}

}
