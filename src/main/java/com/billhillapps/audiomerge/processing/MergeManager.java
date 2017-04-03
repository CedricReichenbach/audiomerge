package com.billhillapps.audiomerge.processing;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import com.billhillapps.audiomerge.music.MusicCollection;
import com.billhillapps.audiomerge.processing.problems.AudioLoadingProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;
import com.billhillapps.audiomerge.processing.problems.StdIOProblemSupervisor;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;
import com.billhillapps.audiomerge.similarity.deciders.NameDistanceArtistDecider;
import com.billhillapps.audiomerge.similarity.deciders.TitleDistanceAlbumDecider;

public class MergeManager extends ProgressAdapter {

	private final Path destination;
	private final Path[] sources;

	private final Statistics statistics = Statistics.getInstance();

	private NameDistanceArtistDecider artistDecider = new NameDistanceArtistDecider();
	private TitleDistanceAlbumDecider albumDecider = new TitleDistanceAlbumDecider();
	private MetaDataDistanceSongDecider songDecider = new MetaDataDistanceSongDecider();

	private ProblemSupervisor<AudioLoadingProblem> cannotReadReviewer = new StdIOProblemSupervisor();

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

	public void setCannotReadReviewer(ProblemSupervisor<AudioLoadingProblem> cannotReadReviewer) {
		this.cannotReadReviewer = cannotReadReviewer;
	}

	public void execute() {
		final List<MusicCollection> collections = new ArrayList<>();
		for (int i = 0; i < sources.length; i++) {
			setCurrentOperation(String.format("Loading collection %s of %s", i + 1, sources.length));
			setProgress(-1);

			Path source = sources[i];
			try {
				collections.add(CollectionIO.fromDirectory(source, artistDecider, albumDecider, songDecider, cannotReadReviewer));
			} catch (IOException e) {
				throw new RuntimeException(String.format("Collection from directory '%s' could not be loaded", source));
			}
		}

		collections.forEach(collection -> statistics.sniffSourceCollection(collection));

		setCurrentOperation("Merging collections");
		setProgress(0);
		final AtomicInteger collectionsMerged = new AtomicInteger(0);
		final BiConsumer<Double, String> mergeProgressListener = (progress, operation) -> {
			setProgress(1d * (collectionsMerged.get() + progress) / collections.size());
		};

		// merge subsequent collections to first
		MusicCollection firstCollection = collections.remove(0);
		firstCollection.addProgressListener(mergeProgressListener);
		for (MusicCollection collection : collections) {
			if (collection == firstCollection)
				continue;

			firstCollection.mergeIn(collection);
			collectionsMerged.incrementAndGet();
		}
		firstCollection.removeProgressListener(mergeProgressListener);

		setCurrentOperation("Resolving similarities");
		setProgress(0);

		firstCollection.addProgressListener((progress, operation) -> setProgress(progress));
		firstCollection.mergeSimilars();

		statistics.sniffResultCollection(firstCollection);

		setCurrentOperation("Saving merged collection to target location");
		firstCollection.saveTo(destination);
	}
}
