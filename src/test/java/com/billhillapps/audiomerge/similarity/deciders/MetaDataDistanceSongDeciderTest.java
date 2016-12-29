package com.billhillapps.audiomerge.similarity.deciders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.test.TestSong;

public class MetaDataDistanceSongDeciderTest {

	MetaDataDistanceSongDecider decider;

	@Before
	public void setUp() throws Exception {
		decider = new MetaDataDistanceSongDecider();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void capitalizationSimilarity() {
		Song songA = new TestSong("HeLlO WoRlD", "-", "-", 0);
		Song songB = new TestSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
	}

	@Test
	public void typoSimilarity() {
		Song songA = new TestSong("halo word", "-", "-", 0);
		Song songB = new TestSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
	}

	@Test
	public void punctuationSimilarity() {
		Song songA = new TestSong("hello, world!", "-", "-", 0);
		Song songB = new TestSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
	}

	@Test
	public void unsimilarities() {
		Song songA = new TestSong("Hello, world!", "-", "-", 0);
		Song songB = new TestSong("Goodbye, cruel world...", "-", "-", 0);
		Song songC = new TestSong("Hello Goodbye", "-", "-", 0);

		assertFalse(decider.areSimilar(songA, songB));
		assertFalse(decider.areSimilar(songA, songC));
		assertFalse(decider.areSimilar(songB, songC));
	}

	@Test
	public void pickHigherBitRate() {
		Song songA = new TestSong("a", "-", "-", 256);
		Song songB = new TestSong("b", "-", "-", 128);
		Song songC = new TestSong("c", "-", "-", 192);

		assertTrue(decider.resolve(songA, songB) < 0);
		assertTrue(decider.resolve(songA, songC) < 0);
		assertTrue(decider.resolve(songB, songC) > 0);
	}
}
