package com.billhillapps.audiomerge.test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.NotImplementedException;

import com.billhillapps.audiomerge.music.Entity;

/**
 * {@link Entity} implementation for testing purposes. Simply contains a name
 * and a collection of strings as content.
 * 
 * @author Cedric Reichenbach
 *
 */
public class MockEntity implements Entity {

	final Collection<String> content = new ArrayList<>();
	public final String name;

	public MockEntity(String name, String[] content) {
		this(name, Arrays.asList(content));
	}

	public MockEntity(String name, Collection<String> content) {
		this.name = name;
		this.content.addAll(content);
	}

	@Override
	public boolean shallowEquals(Entity other) {
		return other instanceof MockEntity && name.equals(((MockEntity) other).name);
	}

	@Override
	public void mergeIn(Entity other) {
		if (!(other instanceof Entity))
			return;

		content.addAll(((MockEntity) other).content);
	}

	@Override
	public void mergeSimilars() {
		throw new RuntimeException("not implemented, this is just a mock class");
	}

	@Override
	public String toString() {
		return String.format("a MockEntity named '%s' with content '%s'", name, content);
	}

	@Override
	public void saveTo(Path path) {
		throw new NotImplementedException("Not needed");
	}
}