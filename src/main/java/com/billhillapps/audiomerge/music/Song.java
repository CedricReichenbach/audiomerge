package com.billhillapps.audiomerge.music;

import org.apache.commons.lang3.StringUtils;

public abstract class Song implements Entity {

	public abstract String getAlbumTitle();

	public abstract String getArtistName();

	public abstract String getTitle();
	
	public abstract long getBitRate();

	public boolean shallowEquals(Entity other) {
		if (!(other instanceof Song))
			return false;

		Song otherSong = (Song) other;
		return StringUtils.equals(otherSong.getTitle(), this.getTitle())
				&& StringUtils.equals(otherSong.getArtistName(), this.getArtistName())
				&& StringUtils.equals(otherSong.getAlbumTitle(), this.getAlbumTitle());
	}
}
