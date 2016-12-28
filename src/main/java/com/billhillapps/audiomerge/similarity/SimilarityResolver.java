package com.billhillapps.audiomerge.similarity;

/**
 * Interface for resolving which instance of a generic type to be continued with
 * in case of a {@link SimilarityChecker}-based collision.
 * 
 * For instance, one could plug in a user interface to let users decide which
 * song to use/drop in case of a collision.
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 *            type of colliding instances
 */
public interface SimilarityResolver<T> {

	/**
	 * Decide which item to continue with.
	 * 
	 * For the first item to be used, return a negative number. Analogously,
	 * return a positive number for the second item to be used.
	 * 
	 * Returning zero signals that the items should not be treated as
	 * equivalent, i.e. both should be continued with separately.
	 * 
	 * @param a
	 * @param b
	 * @return An int < 0 if the first item should be used, an int > 0 for the
	 *         second item respectively, 0 if both should be used separately.
	 */
	public int resolve(T a, T b);

	/**
	 * Similar to {@link #resolve}, pick one of two similar objects to continue
	 * with. But instead of a number indicating which one to be used, directly
	 * return the object in question. If the two are not similar and both should
	 * be used separately, return null.
	 * 
	 * Functionality of this method should always by consistent with
	 * {@link #resolve}.
	 * 
	 * @param a
	 * @param b
	 * @return Item to be used, or null if both should be used (because they're
	 *         not equivalent)
	 */
	public default T pick(T a, T b) {
		int indicator = this.resolve(a, b);
		if (indicator == 0)
			return null;
		return indicator > 0 ? b : a;
	}
}
