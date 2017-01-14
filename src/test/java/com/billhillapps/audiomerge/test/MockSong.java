package com.billhillapps.audiomerge.test;

import java.nio.file.Path;

import org.apache.commons.lang3.NotImplementedException;

import com.billhillapps.audiomerge.music.Song;

public class MockSong extends Song {

	final String title;
	String artistName;
	String albumTitle;
	final long bitRate;

	public MockSong(String title, String artistName, String albumTitle, long bitRate) {
		super();
		this.title = title;
		this.artistName = artistName;
		this.albumTitle = albumTitle;
		this.bitRate = bitRate;
	}

	@Override
	public String getAlbumTitle() {
		return albumTitle;
	}

	@Override
	public String getArtistName() {
		return artistName;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public long getBitRate() {
		return bitRate;
	}

	@Override
	public boolean isVariableBitRate() {
		return false;
	}

	@Override
	public void mergeSimilars() {
		// do nothing
	}

	@Override
	public void saveTo(Path path) {
		throw new NotImplementedException("Not needed");
	}

	@Override
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	@Override
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
}
