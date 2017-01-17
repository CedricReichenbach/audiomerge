package com.billhillapps.audiomerge.processing;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.billhillapps.audiomerge.music.FileSong;
import com.billhillapps.audiomerge.test.BetterQualitySongDecider;
import com.billhillapps.audiomerge.test.LexicographicalAlbumDecider;
import com.billhillapps.audiomerge.test.LexicographicalArtistDecider;
import com.billhillapps.audiomerge.test.problems.IgnoreAllSupervisor;

public class MergeManagerTest {

	@Rule
	public TemporaryFolder destinationFolder = new TemporaryFolder();

	private Path destinationPath;

	private Path collectionPathA;
	private Path collectionPathB;
	private Path collectionPathC;

	@Before
	public void setUp() throws Exception {
		collectionPathA = Paths.get(ClassLoader.getSystemResource("collection-a").toURI());
		collectionPathB = Paths.get(ClassLoader.getSystemResource("collection-b").toURI());
		collectionPathC = Paths.get(ClassLoader.getSystemResource("collection-c").toURI());

		destinationPath = destinationFolder.getRoot().toPath();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void totalMerge() throws Exception {
		MergeManager mergeManager = new MergeManager(destinationPath, collectionPathA, collectionPathB,
				collectionPathC);
		mergeManager.setArtistDecider(new LexicographicalArtistDecider());
		mergeManager.setAlbumDecider(new LexicographicalAlbumDecider());
		mergeManager.setSongDecider(new BetterQualitySongDecider());
		mergeManager.setCannotReadReviewer(new IgnoreAllSupervisor());

		mergeManager.execute();

		assertThat(destinationPath.toFile().list().length, is(4));
		assertThat(Arrays.asList(destinationPath.toFile().list()),
				hasItems("Ludwig van Beethoven", "Hussalonia", "Kevin MacLeod", "Alessandro Moreschi"));

		assertTrue(destinationPath
				.resolve("Ludwig van Beethoven/" + "Unknown Album/"
						+ "Sonata No. 14 in C Sharp Minor Moonlight, Op. 27 No. 2 - I. Adagio sostenuto.mp3")
				.toFile().exists());
		assertTrue(destinationPath.resolve("Hussalonia/" + "The Public Domain EP/" + "This Song Wont Sell A Thing.mp3")
				.toFile().exists());
		assertTrue(destinationPath.resolve("Kevin MacLeod/" + "_Free PD_/" + "Amazing Grace.mp3").toFile().exists());
		assertTrue(destinationPath.resolve("Alessandro Moreschi/" + "Bach - Ave Maria.mp3").toFile().exists());

		Path changedArtistSongPath = destinationPath
				.resolve("Hussalonia/" + "The Public Domain EP/" + "This Song Wont Sell A Thing.mp3");

		assertThat(new FileSong(changedArtistSongPath).getArtistName(), is("Hussalonia"));
	}

}
