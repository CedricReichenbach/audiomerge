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
	private int totalSongsMerged = 0;
	private int similarSongsMerged = 0;
	private int similarAlbumsMerged = 0;
	private int similarArtistsMerged = 0;

	private Statistics() {
	}

	public void sniffSourceCollection(MusicCollection collection) {
		sourceCollectionSizes.add(collection.getAllSongs().size());
	}

	public void sniffResultCollection(MusicCollection collection) {
		resultCollectionSize = collection.getAllSongs().size();
	}

	public void songsMerged() {
		totalSongsMerged++;
	}

	public void similarSongsMerged(int n) {
		similarSongsMerged += n;
	}

	public void similarAlbumsMerged(int n) {
		similarAlbumsMerged += n;
	}

	public void similarArtistsMerged(int n) {
		similarArtistsMerged += n;
	}

	public Collection<Integer> getSourceCollectionSizes() {
		return sourceCollectionSizes;
	}

	public int getResultCollectionSize() {
		return resultCollectionSize;
	}

	public int getTotalSongsMerged() {
		return totalSongsMerged;
	}

	public int getSimilarSongsMerged() {
		return similarSongsMerged;
	}

	public int getSimilarAlbumsMerged() {
		return similarAlbumsMerged;
	}

	public int getSimilarArtistsMerged() {
		return similarArtistsMerged;
	}
}
