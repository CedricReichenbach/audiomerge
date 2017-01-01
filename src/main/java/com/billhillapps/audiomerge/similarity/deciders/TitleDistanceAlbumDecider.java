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
		return DeciderUtil.resolveUsingStdIn(a, b);
	}

}
