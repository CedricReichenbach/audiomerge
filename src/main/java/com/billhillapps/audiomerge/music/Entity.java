package com.billhillapps.audiomerge.music;

public interface Entity {
	/**
	 * Check if two instances are equal except their contents.
	 * 
	 * For example, two {@link Albums} with the same title could be considered
	 * shallow-equal, despite containing different sets of songs.
	 */
	public boolean shallowEquals(Entity other);

	/**
	 * Merge another entity (of the same type) into this one.
	 * 
	 * For instance, this may happen in case of a collision of two equal
	 * {@link Album}s with the same name.
	 */
	public void mergeIn(Entity other);
}
