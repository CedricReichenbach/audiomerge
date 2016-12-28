package com.billhillapps.audiomerge.music;

import java.util.ArrayList;
import java.util.Collection;

public class Album implements Entity {

	private final String albumTitle;
	private final Collection<Song> songs = new ArrayList<>();

	public Album(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void addSong(Song song) {
		songs.add(song);
	}

}
