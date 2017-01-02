package com.billhillapps.audiomerge.processing;

import java.util.function.BiConsumer;

public interface ProgressAware {

	/**
	 * Get progress of current operation in context of this object.
	 * 
	 * @return progress as number in [0, 1]
	 */
	public double getProgress();

	/**
	 * Register listener to be called whenever progress changes during an
	 * operation.
	 * 
	 * @param listener
	 *            consumer receiving progress as number in [0, 1] and string
	 *            describing current operation
	 */
	public void addProgressListener(BiConsumer<Double, String> listener);

	/**
	 * Get a description of the currently running operation.
	 * 
	 * By default, this just returns an empty string.
	 */
	public default String getCurrentOperation() {
		return "";
	}

	/**
	 * Get expected weight (in terms of cost) of this item.
	 * 
	 * For instance, if an operation loads two items, its weight could be
	 * expected to be twice as that of another operation loading just one item.
	 * 
	 * @return weight/cost of operation as positive number - defaults to 1
	 */
	public default double getWeight() {
		return 1;
	}
	
	public void removeProgressListener(BiConsumer<Double, String> listener);
}
