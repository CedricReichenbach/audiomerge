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
import com.billhillapps.audiomerge.test.LexicographicalAlbumDecider;
import com.billhillapps.audiomerge.test.LexicographicalArtistDecider;
import com.billhillapps.audiomerge.test.LexigraphicalSongDecider;

public class MusicCollectionTest {

	MusicCollection collectionA, collectionB, collectionC;

	@Before
	public void setUp() throws Exception {
		Path collectionPathA = Paths.get(ClassLoader.getSystemResource("collection-a").toURI());
		Path collectionPathB = Paths.get(ClassLoader.getSystemResource("collection-b").toURI());
		Path collectionPathC = Paths.get(ClassLoader.getSystemResource("collection-c").toURI());

		collectionA = CollectionIO.fromDirectory(collectionPathA, new LexicographicalArtistDecider(),
				new LexicographicalAlbumDecider(), new LexigraphicalSongDecider());
		collectionB = CollectionIO.fromDirectory(collectionPathB, new LexicographicalArtistDecider(),
				new LexicographicalAlbumDecider(), new LexigraphicalSongDecider());
		collectionC = CollectionIO.fromDirectory(collectionPathC, new LexicographicalArtistDecider(),
				new LexicographicalAlbumDecider(), new LexigraphicalSongDecider());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void mergingSimple() {
		assertThat(collectionB.getArtists(), not(hasItem(isArtist("Hussalonia"))));
		assertThat(collectionB.getAllSongs().size(), is(2));
		assertThat(collectionB.getAllSongs(),
				hasItem(isSong("Sonata No. 14 (Moonlight Sonata)", "Ludwig van Beethoven", "Unknown Album")));
		assertThat(collectionB.getAllSongs(), hasItem(isSong("Amazing Grace", "Kevin MacLeod", "\"Free PD\"")));
		assertThat(collectionB.getAllSongs(),
				not(hasItem(isSong("This Song Wont Sell A Thing", "Hussalonia", "The Public Domain EP"))));

		collectionB.mergeIn(collectionC);

		assertThat(collectionB.getArtists(), hasItem(isArtist("Husxalonia")));
		assertThat(collectionB.getAllSongs(),
				hasItem(isSong("This Song Wont Sell A Thing", "Husxalonia", "The Public Domain EP")));
	}

	@Test
	public void mergingComplex() {
		collectionA.mergeIn(collectionB);
		collectionA.mergeIn(collectionC);

		// there are actually 4 different songs, but 1 of them has two different
		// title and artist spellings, and another 1 has two different album
		// title spellings
		assertThat(collectionA.getAllSongs().size(), is(6));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("Sonata No. 14 (Moonlight Sonata)", "Ludwig van Beethoven", "Unknown Album")));
		assertThat(collectionA.getAllSongs(), hasItem(isSong("Amazing Grace", "Kevin MacLeod", "Free PD")));
		assertThat(collectionA.getAllSongs(), hasItem(isSong("Ave Maria (1904)", "Alessandro Moreschi", "")));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("This Song Won't Sell A Thing.", "Hussalonia", "The Public Domain EP")));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("This Song Wont Sell A Thing", "Husxalonia", "The Public Domain EP")));
	}

	@Test
	public void mergeSimilars() {
		collectionA.mergeIn(collectionB);
		collectionA.mergeIn(collectionC);

		assertThat(collectionA.getAllSongs().size(), is(6));

		collectionA.mergeSimilars();

		assertThat(collectionA.getAllSongs().size(), is(4));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("Sonata No. 14 (Moonlight Sonata)", "Ludwig van Beethoven", "Unknown Album")));
		assertThat(collectionA.getAllSongs(), hasItem(isSong("Amazing Grace", "Kevin MacLeod", "\"Free PD\"")));
		assertThat(collectionA.getAllSongs(), hasItem(isSong("Ave Maria (1904)", "Alessandro Moreschi", "")));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("This Song Won't Sell A Thing.", "Hussalonia", "The Public Domain EP")));
		assertThat(collectionA.getAllSongs(),
				not(hasItem(isSong("This Song Wont Sell A Thing", "Husalonia", "The Public Domain EP"))));
	}

	@Test
	public void selectedArtistReflectedInSong() {
		assertThat(collectionC.getAllSongs(),
				hasItem(isSong("This Song Wont Sell A Thing", "Husxalonia", "The Public Domain EP")));

		collectionA.mergeIn(collectionC);
		collectionA.mergeSimilars();

		assertThat(collectionA.getArtists(), hasItem(isArtist("Hussalonia")));
		assertThat(collectionA.getArtists(), not(hasItem(isArtist("Husxalonia"))));
		assertThat(collectionA.getAllSongs(),
				not(hasItem(isSong("This Song Wont Sell A Thing", "Husxalonia", "The Public Domain EP"))));
		assertThat(collectionA.getAllSongs(),
				not(hasItem(isSong("This Song Won't Sell A Thing.", "Husxalonia", "The Public Domain EP"))));
		assertThat(collectionA.getAllSongs(),
				hasItem(isSong("This Song Won't Sell A Thing.", "Hussalonia", "The Public Domain EP")));
	}
}
