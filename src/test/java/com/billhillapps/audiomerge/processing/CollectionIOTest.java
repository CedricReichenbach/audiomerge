package com.billhillapps.audiomerge.processing;

import static com.billhillapps.audiomerge.test.ArtistMatcher.isArtist;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.music.Artist;
import com.billhillapps.audiomerge.music.MusicCollection;
import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.test.LambdaMatcher;

public class CollectionIOTest {

	Path collectionPathA, collectionPathB, collectionPathC;

	@Before
	public void setUp() throws Exception {
		collectionPathA = Paths.get(ClassLoader.getSystemResource("collection-a").toURI());
		collectionPathB = Paths.get(ClassLoader.getSystemResource("collection-b").toURI());
		collectionPathC = Paths.get(ClassLoader.getSystemResource("collection-c").toURI());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOriginalSongLists() throws Exception {
		MusicCollection collectionA = CollectionIO.fromDirectory(collectionPathA);

		assertThat(collectionA.getTitle(), endsWith("collection-a"));
		assertThat(collectionA.getArtists().size(), is(4));
		assertThat(collectionA.getArtists(), hasItem(isArtist("Ludwig van Beethoven")));
		assertThat(collectionA.getArtists(), hasItem(isArtist("Hussalonia")));
		assertThat(collectionA.getArtists(), hasItem(isArtist("Alessandro Moreschi")));
		assertThat(collectionA.getArtists(), hasItem(isArtist("Kevin MacLeod")));

		assertThat(collectionA.getArtists(), hasItem(LambdaMatcher.<Artist>matches(artist -> {
			Collection<Album> albums = artist.getAlbums();
			if (!artist.getName().equals("Kevin MacLeod") || albums.size() != 1)
				return false;

			Album album = albums.iterator().next();
			Collection<Song> songs = album.getSongs();
			if (!album.getAlbumTitle().equals("Free PD") || songs.size() != 1)
				return false;

			Song song = songs.iterator().next();
			return song.getTitle().equals("Amazing Grace");
		})));

		MusicCollection collectionB = CollectionIO.fromDirectory(collectionPathB);

		assertThat(collectionB.getTitle(), endsWith("collection-b"));
		assertThat(collectionB.getArtists().size(), is(2));
		assertThat(collectionB.getArtists(), hasItem(isArtist("Ludwig van Beethoven")));
		assertThat(collectionB.getArtists(), hasItem(isArtist("Kevin MacLeod")));
		assertThat(collectionB.getArtists(), not(hasItem(isArtist("Hussalonia"))));
		assertThat(collectionB.getArtists(), not(hasItem(isArtist("Alessandro Moreschi"))));

		MusicCollection collectionC = CollectionIO.fromDirectory(collectionPathC);

		assertThat(collectionC.getTitle(), endsWith("collection-c"));
		assertThat(collectionC.getArtists().size(), is(1));
		assertThat(collectionC.getArtists(), hasItem(isArtist("Hussalonia")));
		assertThat(collectionC.getArtists(), not(hasItem(isArtist("Ludwig van Beethoven"))));
		assertThat(collectionC.getArtists(), not(hasItem(isArtist("Kevin MacLeod"))));
		assertThat(collectionC.getArtists(), not(hasItem(isArtist("Alessandro Moreschi"))));
	}

}
