package com.billhillapps.audiomerge.music;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;

import com.billhillapps.audiomerge.processing.ProgressAdapter;
import com.billhillapps.audiomerge.similarity.Decider;

/**
 * Representation of a music collection/library, containing {@link Song}s.
 * 
 * Data is represented in a tree-like structure. Only artists are referenced
 * directly, which themselves contain albums, which contain songs in turn.
 * 
 * @author Cedric Reichenbach
 */
public class MusicCollection extends ProgressAdapter {

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

	public Collection<Song> getAllSongs() {
		Collection<Song> songs = new ArrayList<>();

		artists.asCollection().forEach(artist -> {
			artist.getAlbums().forEach(album -> {
				songs.addAll(album.getSongs());
			});
		});

		return songs;
	}

	public void mergeIn(MusicCollection other) {
		setCurrentOperation("Merge in other collection");
		setProgress(0);
		BiConsumer<Double, String> listener = (progress, description) -> setProgress(progress);

		artists.addProgressListener(listener);
		artists.addAll(other.artists);
		artists.removeProgressListener(listener);
	}

	public void mergeSimilars() {
		artists.mergeSimilars();
		artists.asCollection().forEach(Artist::mergeSimilars);
	}

	public void saveTo(Path path) {
		artists.asCollection().forEach(artist -> artist.saveTo(path));
	}
}
