package com.billhillapps.audiomerge.processing;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.music.FileSong;
import com.billhillapps.audiomerge.music.MusicCollection;
import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.processing.problems.AudioLoadingException;
import com.billhillapps.audiomerge.processing.problems.AudioLoadingProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;
import com.billhillapps.audiomerge.processing.problems.StdIOProblemSupervisor;
import com.billhillapps.audiomerge.similarity.Decider;
import com.billhillapps.audiomerge.similarity.deciders.MetaDataDistanceSongDecider;
import com.billhillapps.audiomerge.similarity.deciders.NameDistanceArtistDecider;
import com.billhillapps.audiomerge.similarity.deciders.TitleDistanceAlbumDecider;

/**
 * Utility functions for loading and saving {@link MusicCollection}s.
 * 
 * @author Cedric Reichenbach
 */
public class CollectionIO {

	private static final PathMatcher MATCHER = FileSystems.getDefault()
			.getPathMatcher("glob:**.{mp3,wma,wav,aiff,aac,ogg,flac,alac}");

	// XXX: Maybe move from static methods to object to mitigate defaults mess
	public static MusicCollection fromDirectory(Path path) throws IOException {
		return fromDirectory(path, new StdIOProblemSupervisor());
	}

	public static MusicCollection fromDirectory(Path path, ProblemSupervisor<AudioLoadingProblem> cannotReadSupervisor)
			throws IOException {
		return fromDirectory(path, false, new NameDistanceArtistDecider(), new TitleDistanceAlbumDecider(),
				new MetaDataDistanceSongDecider(), cannotReadSupervisor);
	}

	public static MusicCollection fromDirectory(Path path, Decider<Artist> artistDecider, Decider<Album> albumDecider,
			Decider<Song> songDecider, ProblemSupervisor<AudioLoadingProblem> cannotReadSupervisor) throws IOException {
		return fromDirectory(path, false, artistDecider, albumDecider, songDecider, cannotReadSupervisor);
	}

	public static MusicCollection fromDirectory(Path path, boolean includeOtherFiles, Decider<Artist> artistDecider,
			Decider<Album> albumDecider, Decider<Song> songDecider,
			ProblemSupervisor<AudioLoadingProblem> cannotReadSupervisor) throws IOException {
		MusicCollection collection = new MusicCollection(path.getFileName().toString(), artistDecider, albumDecider,
				songDecider);

		Stream<Path> filePaths = Files.find(path, Integer.MAX_VALUE,
				(filePath, fileAttributes) -> fileAttributes.isRegularFile()
						&& (includeOtherFiles || MATCHER.matches(filePath)) && !isHidden(filePath));
		try {
			filePaths.forEach(filePath -> {
				try {
					collection.insertSong(songFromFile(filePath));
				} catch (AudioLoadingException e) {
					if (cannotReadSupervisor.ignoreProblem(new AudioLoadingProblem(e, filePath))) {
						Statistics.getInstance().readErrorIgnored();
					} else {
						throw new LoadingFailedException("Failed to load a song, exiting soon...", e);
					}
				}
			});
		} finally {
			filePaths.close();
		}

		return collection;
	}

	/**
	 * Check if a file is hidden.
	 * 
	 * In this context, hidden means either
	 * 
	 * <ul>
	 * <li>The file is considered hidden by the OS</li>
	 * <li>The file or one of its container directories starts with a dot
	 * (".")</li>
	 * </ul>
	 * 
	 * This hybrid behavior is used for better cross-platform consistency, e.g.
	 * in case a collection has historically been copied from an OS X system to
	 * a Windows one.
	 */
	private static boolean isHidden(Path filePath) {
		for (Path part : filePath)
			if (part.toString().startsWith("."))
				return true;

		return filePath.toFile().isHidden();
	}

	private static Song songFromFile(Path filePath) throws AudioLoadingException {
		// TODO: If not music file, treat specially
		return new FileSong(filePath);
	}

	public static void toDirectory(Path path, MusicCollection collection) throws IOException {
		if (!path.toFile().isDirectory())
			throw new RuntimeException(String.format("Path to save collection in is not a directory: '%s'", path));

		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
			if (dirStream.iterator().hasNext())
				throw new RuntimeException(String.format("Directory to save collection to is not empty: '%s'", path));
		}

		collection.saveTo(path);
	}
}
