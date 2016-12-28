package com.billhillapps.audiomerge.music;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/**
 * Implementation of {@link Song}, based on a music file.
 * 
 * @author Cedric Reichenbach
 */
public class FileSong implements Song {

	private final AudioHeader header;
	private final Tag tag;

	public FileSong(Path filePath) {
		try {
			AudioFile audioFile = AudioFileIO.read(filePath.toFile());
			header = audioFile.getAudioHeader();
			tag = audioFile.getTag();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			throw new RuntimeException(String.format("Failed to read file '%s' for metadata", filePath), e);
		}
	}

	@Override
	public String getAlbumTitle() {
		return tryTags(FieldKey.ALBUM, FieldKey.ORIGINAL_ALBUM);
	}

	@Override
	public String getArtistName() {
		return tryTags(FieldKey.ALBUM_ARTIST, FieldKey.ARTIST, FieldKey.ORIGINAL_ARTIST);
	}

	@Override
	public String getTitle() {
		return tryTags(FieldKey.TITLE);
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
			String value = tag.getFirst(fieldKey);

			if (StringUtils.isNotBlank(value))
				return StringUtils.trim(value);
		}

		return "";
	}

}
