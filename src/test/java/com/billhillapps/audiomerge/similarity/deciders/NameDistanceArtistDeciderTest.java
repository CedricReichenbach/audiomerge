package com.billhillapps.audiomerge.similarity.deciders;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.music.Artist;

public class NameDistanceArtistDeciderTest {

	private NameDistanceArtistDecider decider;

	@Before
	public void setUp() throws Exception {
		decider = new NameDistanceArtistDecider();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void specialCharacterSimilarity() {
		Artist artistA = new Artist("R.E.M.", null, null);
		Artist artistB = new Artist("rem", null, null);

		assertTrue(decider.areSimilar(artistA, artistB));
	}

	@Test
	public void beyonceWithOrWithoutKnowles() {
		Artist artistA = new Artist("Beyoncé", null, null);
		Artist artistB = new Artist("Beyoncé Knowles", null, null);

		assertTrue(decider.areSimilar(artistA, artistB));
	}

	@Test
	public void pharrellWithOrWithoutWilliams() {
		Artist artistA = new Artist("Pharrell", null, null);
		Artist artistB = new Artist("Pharrell Williams", null, null);

		assertTrue(decider.areSimilar(artistA, artistB));
	}

	@Test
	public void umlautSimilarity() {
		Artist artistA = new Artist("Gölä", null, null);
		Artist artistB = new Artist("gola", null, null);

		assertTrue(decider.areSimilar(artistA, artistB));
	}

	@Test
	public void differentBandsWithSimilarLetters() {
		Artist artistA = new Artist("Eagles", null, null);
		Artist artistB = new Artist("Bangles", null, null);

		assertFalse(decider.areSimilar(artistA, artistB));
	}
}
