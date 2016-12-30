package com.billhillapps.audiomerge.music;

import static com.billhillapps.audiomerge.test.ArtistMatcher.isArtist;
import static com.billhillapps.audiomerge.test.SongMatcher.isSong;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.processing.CollectionIO;

public class MusicCollectionTest {

	MusicCollection collectionA, collectionB, collectionC;

	@Before
	public void setUp() throws Exception {
		Path collectionPathA = Paths.get(ClassLoader.getSystemResource("collection-a").toURI());
		Path collectionPathB = Paths.get(ClassLoader.getSystemResource("collection-b").toURI());
		Path collectionPathC = Paths.get(ClassLoader.getSystemResource("collection-c").toURI());

		collectionA = CollectionIO.fromDirectory(collectionPathA);
		collectionB = CollectionIO.fromDirectory(collectionPathB);
		collectionC = CollectionIO.fromDirectory(collectionPathC);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void merging() {
		assertThat(collectionB.getArtists(), not(hasItem(isArtist("Hussalonia"))));
		assertThat(collectionB.getAllSongs().size(), is(2));
		assertThat(collectionB.getAllSongs(),
				hasItem(isSong("Sonata No. 14 (Moonlight Sonata)", "Ludwig van Beethoven", "Unknown Album")));
		assertThat(collectionB.getAllSongs(), hasItem(isSong("Amazing Grace", "Kevin MacLeod", "Free PD")));
		assertThat(collectionB.getAllSongs(),
				not(hasItem(isSong("This Song Won't Sell A Thing", "Hussalonia", "The Public Domain EP"))));

		collectionB.mergeIn(collectionC);

		assertThat(collectionB.getArtists(), hasItem(isArtist("Hussalonia")));
		assertThat(collectionB.getAllSongs(),
				hasItem(isSong("This Song Wont Sell A Thing", "Hussalonia", "The Public Domain EP")));
	}

}
