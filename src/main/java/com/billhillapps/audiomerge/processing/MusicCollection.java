package com.billhillapps.audiomerge.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.music.Song;

/**
 * Representation of a music collection/library, containing {@link Song}s.
 * 
 * Data is represented in a tree-like structure. Only artists are referenced
 * directly, which themselves contain albums, which contain songs in turn.
 * 
 * @author Cedric Reichenbach
 */
public class MusicCollection {

	private final String title;

	/**
	 * Map from artist names to artist objects.
	 */
	private final Map<String, Artist> artists = new HashMap<>();

	public MusicCollection(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void insertSong(Song song) {
		String artistName = song.getArtistName();

		// assume same name <=> same artist
		if (!artists.containsKey(artistName))
			artists.put(artistName, new Artist(artistName));

		artists.get(artistName).insertSong(song);
	}

	public Collection<Artist> getArtists() {
		return new ArrayList<Artist>(artists.values());
	}

}
