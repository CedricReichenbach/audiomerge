package com.billhillapps.audiomerge.processing;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.billhillapps.audiomerge.test.BetterQualitySongDecider;
import com.billhillapps.audiomerge.test.LexicographicalAlbumDecider;
import com.billhillapps.audiomerge.test.LexicographicalArtistDecider;
import com.billhillapps.audiomerge.test.TestUtil;

public class StatisticsTest {

	Statistics statistics;

	@Rule
	public TemporaryFolder destinationFolder = new TemporaryFolder();

	private Path destinationPath;

	private Path collectionPathA;
	private Path collectionPathB;
	private Path collectionPathC;

	private MergeManager mergeManager;

	@Before
	public void setUp() throws Exception {
		Statistics.reset();
		statistics = Statistics.getInstance();

		collectionPathA = Paths.get(ClassLoader.getSystemResource("collection-a").toURI());
		collectionPathB = Paths.get(ClassLoader.getSystemResource("collection-b").toURI());
		collectionPathC = Paths.get(ClassLoader.getSystemResource("collection-c").toURI());
		destinationPath = destinationFolder.getRoot().toPath();

		mergeManager = new MergeManager(destinationPath, collectionPathA, collectionPathB, collectionPathC);

		mergeManager.setArtistDecider(new LexicographicalArtistDecider());
		mergeManager.setAlbumDecider(new LexicographicalAlbumDecider());
		mergeManager.setSongDecider(new BetterQualitySongDecider());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void initialValues() {
		assertThat(statistics.getSimilarSongsMerged(), is(0));
		assertThat(statistics.getTotalSongsMerged(), is(0));
		assertThat(statistics.getResultCollectionSize(), is(0));
		assertThat(statistics.getSourceCollectionSizes().size(), is(0));
	}

	@Test
	public void mergeStatsRecorded() {
		TestUtil.runNTimes(67, () -> statistics.songsMerged());
		TestUtil.runNTimes(5, () -> statistics.similarSongsMerged(7));
		TestUtil.runNTimes(3, () -> statistics.similarAlbumsMerged(4));
		TestUtil.runNTimes(2, () -> statistics.similarArtistsMerged(3));

		assertThat(statistics.getTotalSongsMerged(), is(67));
		assertThat(statistics.getSimilarSongsMerged(), is(35));
		assertThat(statistics.getSimilarAlbumsMerged(), is(12));
		assertThat(statistics.getSimilarArtistsMerged(), is(6));
	}

	@Test
	public void fullRunRecordsCorrectly() {
		mergeManager.execute();

		assertThat(statistics.getSourceCollectionSizes().size(), is(3));
		assertThat(statistics.getSourceCollectionSizes(), hasItems(4, 2, 1));
		assertThat(statistics.getResultCollectionSize(), is(4));
		assertThat(statistics.getTotalSongsMerged(), is(3));
		assertThat(statistics.getSimilarSongsMerged(), is(2));
		assertThat(statistics.getSimilarAlbumsMerged(), is(1));
		assertThat(statistics.getSimilarArtistsMerged(), is(1));
	}
}
