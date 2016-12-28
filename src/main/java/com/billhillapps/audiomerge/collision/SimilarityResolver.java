package com.billhillapps.audiomerge.collision;

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
	 * return a positive number for the second item to be used. Returning zero
	 * signals that it doesn't matter, i.e. which item to be taken is undefined.
	 * 
	 * @param a
	 * @param b
	 * @return An int < 0 if the first item should be used, an int > 0 for the
	 *         second item respectively, 0 if undecided.
	 */
	public int resolve(T a, T b);

	/**
	 * Similar to {@link #resolve}, pick one of two similar objects to continue
	 * with. But instead of a number indicating which one to be used, directly
	 * return the object in question.
	 * 
	 * Functionality of this method should always by consisten with
	 * {@link #resolve}.
	 * 
	 * @param a
	 * @param b
	 * @return Item to be used
	 */
	public default T pick(T a, T b) {
		return this.resolve(a, b) > 0 ? b : a;
	}
}
