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
		return DeciderUtil.resolveUsingStdIn(a, b, betterQuality(a, b) <= 0 ? a : b);
	}

	protected int betterQuality(Song a, Song b) {
		// TODO: Consider other aspects than bitrate?

		// -, because we want the higher bit rate (sort descending)
		return -Long.compare(a.getBitRate(), b.getBitRate());
	}

}
