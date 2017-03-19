package com.billhillapps.audiomerge.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.billhillapps.audiomerge.processing.problems.AudioLoadingException;

/**
 * Implementation of {@link Song}, based on a music file.
 * 
 * @author Cedric Reichenbach
 */
public class FileSong extends Song {

	private final Path originalPath;
	private final AudioHeader header;
	private final Tag tag;

	private String albumTitleOverride = null;
	private String artistNameOverride = null;

	public FileSong(final Path filePath) throws AudioLoadingException {
		this.originalPath = filePath;

		try {
			AudioFile audioFile = AudioFileIO.read(filePath.toFile());
			header = audioFile.getAudioHeader();

			tag = audioFile.getTagOrCreateDefault();
		} catch (CannotReadException | InvalidAudioFrameException e) {
			throw new AudioLoadingException(e);
		} catch (IOException | TagException | ReadOnlyFileException e) {
			throw new RuntimeException(String.format("Failed to read file '%s' for metadata", filePath), e);
		}
	}

	@Override
	public String getAlbumTitle() {
		if (albumTitleOverride != null)
			return albumTitleOverride;

		return tryTags(FieldKey.ALBUM, FieldKey.ORIGINAL_ALBUM);
	}

	@Override
	public void setAlbumTitle(String albumTitle) {
		this.albumTitleOverride = albumTitle;
	}

	@Override
	public String getArtistName() {
		if (artistNameOverride != null)
			return artistNameOverride;

		return tryTags(FieldKey.ALBUM_ARTIST, FieldKey.ARTIST, FieldKey.ORIGINAL_ARTIST);
	}

	@Override
	public void setArtistName(String artistName) {
		this.artistNameOverride = artistName;
	}

	@Override
	public String getTitle() {
		return tryTags(FieldKey.TITLE);
	}

	@Override
	public long getBitRate() {
		return header.getBitRateAsNumber();
	}

	@Override
	public boolean isVariableBitRate() {
		return header.isVariableBitRate();
	}

	/**
	 * Try reading tags in given order. Start with first and continue with next
	 * as long as previous ones are not found.
	 * 
	 * @return Content of the first existing tag as string, or empty string if
	 *         none was found (to be consistent with JAudioTagger)
	 */
	private String tryTags(FieldKey... fieldKeys) {
		for (FieldKey fieldKey : fieldKeys) {
			String value;
			try {
				value = tag.getFirst(fieldKey);
			} catch (UnsupportedOperationException e) {
				continue;
			}

			if (StringUtils.isNotBlank(value))
				return StringUtils.trim(value);
		}

		return "";
	}

	@Override
	public void mergeIn(Entity other) {
		super.mergeIn(other);

		// do nothing for now, as songs cannot really be "merged"

		// TODO: Maybe merge meta data by filling empty spots by ones from the
		// other
	}

	@Override
	public void mergeSimilars() {
		// do nothing, since there's no child content
	}

	@Override
	public void saveTo(Path path) {
		Path filePath = createUniqueTargetPath(path);
		try {
			Files.copy(originalPath, filePath);

			if (albumTitleOverride != null || artistNameOverride != null)
				overrideMetaData(filePath);
		} catch (IOException e) {
			// XXX: Better handling (Exception system)
			throw new RuntimeException(String.format("Failed to copy song from '%s' to '%s'", originalPath, filePath),
					e);
		}
	}

	private Path createUniqueTargetPath(Path path) {
		Path filePath = path.resolve(originalPath.getFileName());
		int fileSuffixNumber = 1;
		while (Files.exists(filePath)) {
			// TODO: Maybe use decider instead of directly adding a hard-coded
			// suffix
			final String fileSuffix = String.format("(%s)", ++fileSuffixNumber);
			final String bareName = FilenameUtils.removeExtension(originalPath.toFile().getName());
			final String extension = FilenameUtils.getExtension(originalPath.toFile().getName());

			filePath = path.resolve(String.format("%s%s.%s", bareName, fileSuffix, extension));
		}
		return filePath;
	}

	public void overrideMetaData(final Path filePath) throws IOException {
		try {
			AudioFile targetAudioFile = AudioFileIO.read(filePath.toFile());
			Tag audioTag = targetAudioFile.getTagOrCreateAndSetDefault();

			if (albumTitleOverride != null)
				audioTag.setField(FieldKey.ALBUM, albumTitleOverride);

			if (artistNameOverride != null) {
				try {
					// FIXME: Throws UnsupportedOperationException (for WAV files?)
					audioTag.setField(FieldKey.ALBUM_ARTIST, artistNameOverride);
				} catch (NullPointerException e) {
					// setField sometimes throws a NPE because certain tags
					// (like ID3v1) don't support ALBUM_ARTIST
					audioTag.setField(FieldKey.ARTIST, artistNameOverride);
				}
			}

			boolean writeProtectionLifted = false;
			if (!filePath.toFile().canWrite())
				// TODO: Maybe log something here (using a proper logger)?
				writeProtectionLifted = filePath.toFile().setWritable(true);

			targetAudioFile.commit();

			if (writeProtectionLifted)
				filePath.toFile().setWritable(false);
		} catch (KeyNotFoundException | CannotReadException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException | CannotWriteException e) {
			throw new RuntimeException(String.format("Failed to override meta data in target file '%s'", filePath), e);
		} catch (SecurityException e) {
			throw new RuntimeException(String.format("Failed to check write protection on target file '%s'", filePath),
					e);
		}
	}

	@Override
	public Path getPath() {
		return this.originalPath;
	}
}
