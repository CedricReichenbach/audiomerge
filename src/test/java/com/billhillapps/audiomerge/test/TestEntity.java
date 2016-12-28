package com.billhillapps.audiomerge.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.billhillapps.audiomerge.music.Entity;

/**
 * {@link Entity} implementation for testing purposes. Simply contains a
 * name and a collection of strings as content.
 * 
 * @author Cedric Reichenbach
 *
 */
public class TestEntity implements Entity {

	final Collection<String> content = new ArrayList<>();
	public final String name;

	public TestEntity(String name, String[] content) {
		this(name, Arrays.asList(content));
	}

	public TestEntity(String name, Collection<String> content) {
		this.name = name;
		this.content.addAll(content);
	}

	@Override
	public boolean shallowEquals(Entity other) {
		return other instanceof TestEntity && name.equals(((TestEntity) other).name);
	}

	@Override
	public void mergeIn(Entity other) {
		if (!(other instanceof Entity))
			return;

		content.addAll(((TestEntity) other).content);
	}
}