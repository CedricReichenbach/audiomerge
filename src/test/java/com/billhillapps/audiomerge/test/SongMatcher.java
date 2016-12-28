package com.billhillapps.audiomerge.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.billhillapps.audiomerge.music.Song;

public class SongMatcher extends BaseMatcher<Song> {

	private final String title;
	private final String artist;
	private final String album;

	private SongMatcher(String title, String artist, String album) {
		this.title = title;
		this.artist = artist;
		this.album = album;
	}

	public static SongMatcher isSong(String title, String artist, String album) {
		return new SongMatcher(title, artist, album);
	}

	@Override
	public boolean matches(Object item) {
		if (!(item instanceof Song))
			return false;

		Song song = (Song) item;
		return song.getTitle().equals(title) && song.getArtistName().equals(artist)
				&& song.getAlbumTitle().equals(album);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Song object with title ").appendValue(title);
		description.appendText(", artist ").appendValue(artist);
		description.appendText(", album ").appendValue(album);
	}

}
