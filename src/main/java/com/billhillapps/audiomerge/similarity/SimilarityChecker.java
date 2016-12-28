package com.billhillapps.audiomerge.similarity;

/**
 * Checker able to compare two instances of a generic type and determine whether
 * they are similar or equal.
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 *            type of objects to be compared
 */
public interface SimilarityChecker<T> {

	/**
	 * Indicate whether two items are similar.
	 * 
	 * For instance, two terms with almost identical titles but one with and one
	 * without diacritics, like "Antonin Dvorak"/"Antonín Dvořák", could be
	 * considered similar.
	 * 
	 * @param a
	 * @param b
	 */
	public boolean areSimilar(T a, T b);
}
