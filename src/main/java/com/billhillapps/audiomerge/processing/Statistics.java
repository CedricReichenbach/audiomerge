package com.billhillapps.audiomerge.processing;

import java.util.ArrayList;
import java.util.Collection;

import com.billhillapps.audiomerge.music.MusicCollection;

public class Statistics {

	private static Statistics instance;

	public static Statistics getInstance() {
		if (instance == null)
			instance = new Statistics();

		return instance;
	}

	public static void reset() {
		instance = null;
	}

	private final Collection<Integer> sourceCollectionSizes = new ArrayList<>();
	private int resultCollectionSize;

	// TODO: Count merged direct matches and similarities

	private Statistics() {
	}

	public void sniffSourceCollection(MusicCollection collection) {
		sourceCollectionSizes.add(collection.getAllSongs().size());
	}

	public void sniffResultCollection(MusicCollection collection) {
		resultCollectionSize = collection.getAllSongs().size();
	}

	public Collection<Integer> getSourceCollectionSizes() {
		return sourceCollectionSizes;
	}

	public int getResultCollectionSize() {
		return resultCollectionSize;
	}
}
