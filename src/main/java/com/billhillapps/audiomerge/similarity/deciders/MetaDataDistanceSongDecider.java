package com.billhillapps.audiomerge.similarity.deciders;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.similarity.Decider;

public class MetaDataDistanceSongDecider implements Decider<Song> {

	@Override
	public boolean areSimilar(Song a, Song b) {
		// TODO: Consider more metadata
		return DistanceUtil.areSimilar(a.getTitle(), b.getTitle());
	}

	@Override
	public int resolve(Song a, Song b) {
		// TODO: Consider other aspects than bitrate? Maybe ask user?
		DeciderUtil.resolveUsingStdIn(a, b, a.getBitRate() >= b.getBitRate() ? a : b);
		return b.getBitRate() > a.getBitRate() ? 1 : -1;
	}

}
