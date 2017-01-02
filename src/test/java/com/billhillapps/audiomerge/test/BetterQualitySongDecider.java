package com.billhillapps.audiomerge.test;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;

public class BetterQualitySongDecider extends MetaDataDistanceSongDecider {

	@Override
	public int resolve(Song a, Song b) {
		return betterQuality(a, b) * 2 - 1;
	}
}
