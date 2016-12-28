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
		// TODO: Ask user before merging? (Return 0 if user refuses to merge)
		// TODO: Consider other aspects than bitrate? Maybe ask user?
		System.out.println(String.format("Deciding between songs '%s' and '%s'", a, b));
		if (b.getBitRate() > a.getBitRate())
			return 1;
		return -1;
	}

}
