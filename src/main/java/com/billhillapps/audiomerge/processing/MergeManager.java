package com.billhillapps.audiomerge.processing;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.billhillapps.audiomerge.music.MusicCollection;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;
import com.billhillapps.audiomerge.similarity.deciders.NameDistanceArtistDecider;
import com.billhillapps.audiomerge.similarity.deciders.TitleDistanceAlbumDecider;

public class MergeManager extends ProgressAdapter {

	private final Path destination;
	private final Path[] sources;

	private NameDistanceArtistDecider artistDecider = new NameDistanceArtistDecider();
	private TitleDistanceAlbumDecider albumDecider = new TitleDistanceAlbumDecider();
	private MetaDataDistanceSongDecider songDecider = new MetaDataDistanceSongDecider();

	public MergeManager(Path destination, Path... sources) {
		if (sources.length < 1)
			throw new RuntimeException("No source directories specified");

		this.destination = destination;
		this.sources = sources;
	}

	public Path getDestination() {
		return destination;
	}

	public void setArtistDecider(NameDistanceArtistDecider artistDecider) {
		this.artistDecider = artistDecider;
	}

	public void setAlbumDecider(TitleDistanceAlbumDecider albumDecider) {
		this.albumDecider = albumDecider;
	}

	public void setSongDecider(MetaDataDistanceSongDecider songDecider) {
		this.songDecider = songDecider;
	}

	public void execute() {
		setCurrentOperation("Loading collections");
		setProgress(0);

		List<MusicCollection> collections = new ArrayList<>();
		for (int i = 0; i < sources.length; i++) {
			Path source = sources[i];
			try {
				collections.add(CollectionIO.fromDirectory(source, artistDecider, albumDecider, songDecider));
			} catch (IOException e) {
				throw new RuntimeException(String.format("Collection from directory '%s' could not be loaded", source));
			}
			setProgress(1d * i / sources.length);
		}

		setCurrentOperation("Merging collections");
		setProgress(0);

		// merge subsequent collections to first
		MusicCollection firstCollection = collections.remove(0);
		collections.forEach(collection -> {
			firstCollection.mergeIn(collection);
		});

		firstCollection.mergeSimilars();

		firstCollection.saveTo(destination);
	}
}
