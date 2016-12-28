package com.billhillapps.audiomerge.music;

import java.util.HashMap;
import java.util.Map;

public class Artist implements Entity {

	private final String name;
	private final Map<String, Album> albums = new HashMap<>();

	public Artist(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void insertSong(Song song) {
		String albumTitle = song.getAlbumTitle();

		// assume same title <=> same album
		if (!albums.containsKey(albumTitle))
			albums.put(albumTitle, new Album(albumTitle));

		albums.get(albumTitle).addSong(song);
	}

	public String toString() {
		return String.format("an Artist named '%s'", name);
	}
}
