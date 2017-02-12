package com.billhillapps.audiomerge.similarity.deciders;

import static com.billhillapps.audiomerge.test.TestUtil.simulateStdInput;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.music.Song;
import com.billhillapps.audiomerge.test.MockSong;

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
		Song songA = new MockSong("HeLlO WoRlD", "-", "-", 0);
		Song songB = new MockSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
	}

	@Test
	public void typoSimilarity() {
		Song songA = new MockSong("helo word", "-", "-", 0);
		Song songC = new MockSong("halo world", "-", "-", 0);
		Song songB = new MockSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
		assertTrue(decider.areSimilar(songA, songC));
		assertTrue(decider.areSimilar(songB, songC));
	}

	@Test
	public void punctuationSimilarity() {
		Song songA = new MockSong("hello, world!", "-", "-", 0);
		Song songB = new MockSong("hello world", "-", "-", 0);

		assertTrue(decider.areSimilar(songA, songB));
	}

	@Test
	public void unsimilarities() {
		Song songA = new MockSong("Hello, world!", "-", "-", 0);
		Song songB = new MockSong("Goodbye, cruel world...", "-", "-", 0);
		Song songC = new MockSong("Hello Goodbye", "-", "-", 0);

		assertFalse(decider.areSimilar(songA, songB));
		assertFalse(decider.areSimilar(songA, songC));
		assertFalse(decider.areSimilar(songB, songC));
	}

	@Test
	public void pickHigherBitRate() {
		Song songA = new MockSong("a", "-", "-", 256);
		Song songB = new MockSong("b", "-", "-", 128);
		Song songC = new MockSong("c", "-", "-", 192);

		// simulate picking "[d]efault" every time
		simulateStdInput("d", "d", "d");

		assertTrue(decider.resolve(songA, songB) < 0);
		assertTrue(decider.resolve(songA, songC) < 0);
		assertTrue(decider.resolve(songB, songC) > 0);
	}
}
