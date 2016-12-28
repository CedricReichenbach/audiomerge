package com.billhillapps.audiomerge.similarity;

/**
 * Interface combining similarity checking and resolving for convenience.
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 *            Type of items to be compared
 */
public interface Decider<T> extends SimilarityChecker<T>, SimilarityResolver<T> {

}
