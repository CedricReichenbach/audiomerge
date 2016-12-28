package com.billhillapps.audiomerge.music;

import java.util.Collection;

import com.billhillapps.audiomerge.similarity.Decider;

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
	private final Decider<Album> albumDecider;
	private final Decider<Song> songDecider;

	private final EntityBag<Artist> artists;

	public MusicCollection(String title, Decider<Artist> artistDecider, Decider<Album> albumDecider,
			Decider<Song> songDecider) {
		this.title = title;
		this.albumDecider = albumDecider;
		this.songDecider = songDecider;

		artists = new EntityBag<>(artistDecider);
	}

	public String getTitle() {
		return title;
	}

	public void insertSong(Song song) {
		String artistName = song.getArtistName();
		Artist artist = new Artist(artistName, albumDecider, songDecider);
		artist.insertSong(song);

		artists.add(artist);
	}

	public Collection<Artist> getArtists() {
		return artists.asCollection();
	}

}
