package com.billhillapps.audiomerge.test;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class MockEntityMatcher extends BaseMatcher<MockEntity> {

	private final String name;
	private final String[] content;

	private MockEntityMatcher(String name, String[] content) {
		super();
		if (name == null & content == null)
			throw new RuntimeException("Name and content are both null - what am I supposed to match?");

		this.name = name;
		this.content = content;
	}

	public static MockEntityMatcher isTestEntity(String name) {
		return new MockEntityMatcher(name, null);
	}

	public static MockEntityMatcher isTestEntity(String[] content) {
		return new MockEntityMatcher(null, content);
	}

	public static MockEntityMatcher isTestEntity(String name, String[] content) {
		return new MockEntityMatcher(name, content);
	}

	@Override
	public boolean matches(Object item) {
		if (!(item instanceof MockEntity))
			return false;

		MockEntity entity = (MockEntity) item;

		boolean result = true;
		if (name != null)
			result &= entity.name.equals(this.name);
		if (content != null)
			result &= Arrays.equals(entity.content.toArray(), this.content);

		return result;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("TestEntity with ");
		if (name != null)
			description.appendText("name ").appendValue(name);
		if (name != null && content != null)
			description.appendText(" and ");
		if (content != null)
			description.appendText("content ").appendValue(content);
	}

}
