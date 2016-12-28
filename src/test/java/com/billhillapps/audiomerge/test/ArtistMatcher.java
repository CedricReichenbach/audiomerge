package com.billhillapps.audiomerge.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.billhillapps.audiomerge.music.Artist;

public class ArtistMatcher extends BaseMatcher<Artist> {

	String artistName;

	private ArtistMatcher(String artistName) {
		this.artistName = artistName;
	}

	public static ArtistMatcher isArtist(String artistName) {
		return new ArtistMatcher(artistName);
	}

	@Override
	public boolean matches(Object item) {
		return item instanceof Artist && ((Artist) item).getName().equals(artistName);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Artist with name ").appendValue(artistName);
	}

}
