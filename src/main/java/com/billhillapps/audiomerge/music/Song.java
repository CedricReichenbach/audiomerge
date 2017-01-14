package com.billhillapps.audiomerge.music;

import org.apache.commons.lang3.StringUtils;

import com.billhillapps.audiomerge.processing.ProgressAdapter;
import com.billhillapps.audiomerge.processing.Statistics;

public abstract class Song extends ProgressAdapter implements Entity {

	// TODO: Support some kind of selective meta data merging (e.g. content from
	// A, but title from B)

	public abstract String getAlbumTitle();

	public abstract void setAlbumTitle(String albumTitle);

	/**
	 * Return the name of this song's album artist.
	 */
	public abstract String getArtistName();

	/**
	 * Override the name of this song's album artist.
	 */
	public abstract void setArtistName(String artistName);

	public abstract String getTitle();

	public abstract long getBitRate();
	
	public abstract boolean isVariableBitRate();

	// XXX: Strong enough?
	public boolean shallowEquals(Entity other) {
		if (!(other instanceof Song))
			return false;

		Song otherSong = (Song) other;
		return StringUtils.equals(otherSong.getTitle(), this.getTitle())
				&& StringUtils.equals(otherSong.getArtistName(), this.getArtistName())
				&& StringUtils.equals(otherSong.getAlbumTitle(), this.getAlbumTitle())
				&& otherSong.getBitRate() == this.getBitRate();
	}

	@Override
	public void mergeIn(Entity other) {
		Statistics.getInstance().songsMerged();
	}

	public String toString() {
		return String.format("a Song titled '%s' by '%s' from '%s'", getTitle(), getArtistName(), getAlbumTitle());
	}
}
