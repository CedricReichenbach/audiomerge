package com.billhillapps.audiomerge.processing;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

import com.billhillapps.audiomerge.music.Song;

/**
 * Utility functions for loading and saving {@link MusicCollection}s.
 * 
 * @author Cedric Reichenbach
 */
public class CollectionIO {

	private static final PathMatcher MATCHER = FileSystems.getDefault()
			.getPathMatcher("*.{mp3,wma,wav,aiff,aac,ogg,flac,alac}");

	public static MusicCollection fromDirectory(Path path, boolean includeOtherFiles) throws IOException {
		MusicCollection collection = new MusicCollection(path.getFileName().toString());
		
		Stream<Path> files = Files.find(path, Integer.MAX_VALUE,
				(filePath, fileAttributes) -> fileAttributes.isRegularFile() && (includeOtherFiles || MATCHER.matches(filePath)));
		files.forEach(file ->
			collection.insertSong(songFromFile(file))
		);
		files.close();
		
		return collection;
	}

	private static Song songFromFile(Path file) {
		// TODO: If not music file, treat specially
		// TODO: Implement
		throw new RuntimeException("not implemented");
	}
}
