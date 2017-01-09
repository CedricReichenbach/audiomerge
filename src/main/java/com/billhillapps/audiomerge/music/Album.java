package com.billhillapps.audiomerge.music;

import java.nio.file.Path;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.billhillapps.audiomerge.processing.PathUtil;
import com.billhillapps.audiomerge.processing.ProgressAdapter;
import com.billhillapps.audiomerge.processing.Statistics;
import com.billhillapps.audiomerge.similarity.Decider;

public class Album extends ProgressAdapter implements Entity {

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

		otherAlbum.songs.forEach(song -> song.setAlbumTitle(this.getAlbumTitle()));
		songs.addAll(otherAlbum.songs);
	}

	@Override
	public String toString() {
		return String.format("an Album titled %s with %s songs", albumTitle, songs.size());
	}

	public Collection<Song> getSongs() {
		return songs.asCollection();
	}

	@Override
	public void mergeSimilars() {
		int merged = songs.mergeSimilars();
		Statistics.getInstance().similarSongsMerged(merged);

		songs.asCollection().forEach(Song::mergeSimilars);
	}

	@Override
	public void saveTo(final Path path) {
		String dirName = this.getAlbumTitle();

		Path subPath;
		if (StringUtils.isNotBlank(dirName))
			subPath = PathUtil.createSafeSubpath(path, dirName);
		else
			subPath = path;

		subPath.toFile().mkdirs();
		songs.asCollection().forEach(song -> song.saveTo(subPath));
	}

	@Override
	public double getWeight() {
		return songs.getWeight();
	}
}
