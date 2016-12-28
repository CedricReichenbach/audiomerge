package com.billhillapps.audiomerge.music;

import org.apache.commons.lang3.StringUtils;

import com.billhillapps.audiomerge.similarity.Decider;

public class Album implements Entity {

	private final String albumTitle;
	private final EntityBag<Song> songs;

	public Album(String albumTitle, Decider<Song> decider) {
		this.albumTitle = albumTitle;
		songs = new EntityBag<>(decider);
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void addSong(Song song) {
		songs.add(song);
	}

	public boolean shallowEquals(Entity other) {
		if (!(other instanceof Album))
			return false;

		return StringUtils.equals(((Album) other).albumTitle, this.albumTitle);
	}

	@Override
	public void mergeIn(Entity other) {
		if (!(other instanceof Album))
			throw new RuntimeException("Cannot merge different types");

		Album otherAlbum = (Album) other;
		songs.addAll(otherAlbum.songs);
	}
}
