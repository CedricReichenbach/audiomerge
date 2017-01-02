package com.billhillapps.audiomerge.music;

import java.nio.file.Path;

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

	/**
	 * Merge similar items into one each, then recursively trigger
	 * similar-merging for all contained items.
	 */
	public void mergeSimilars();

	/**
	 * Save this entity to a file system path. If this entity contains
	 * sub-entities, those should recursively be saved too.
	 */
	public void saveTo(Path path);

	// TODO: Maybe create AbstractEntity with some common implementations
	// currently duplicated in Artist and Album
}
