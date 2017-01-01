package com.billhillapps.audiomerge.processing;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.billhillapps.audiomerge.music.MusicCollection;

public class MergeManager {

	private final Path[] sources;

	public MergeManager(Path destination, Path... sources) {
		if (sources.length < 1)
			throw new RuntimeException("No source directories specified");

		this.sources = sources;

	}

	public void execute() {
		List<MusicCollection> collections = Arrays.asList(sources).stream().map(source -> {
			try {
				return CollectionIO.fromDirectory(source);
			} catch (IOException e) {
				throw new RuntimeException(String.format("Collection from directory '%s' could not be loaded", source));
			}
		}).collect(Collectors.toList());

		// merge subsequent collections to first
		MusicCollection firstCollection = collections.remove(0);
		collections.forEach(firstCollection::mergeIn);

		firstCollection.mergeSimilars();

		// TODO: Save to destination
	}
}
