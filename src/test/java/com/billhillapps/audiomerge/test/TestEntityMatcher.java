package com.billhillapps.audiomerge.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class TestEntityMatcher extends BaseMatcher<TestEntity> {

	private final String name;
	private final String[] content;

	private TestEntityMatcher(String name, String[] content) {
		super();
		if (name == null & content == null)
			throw new RuntimeException("Name and content are both null - what am I supposed to match?");

		this.name = name;
		this.content = content;
	}

	public static TestEntityMatcher isTestEntity(String name) {
		return new TestEntityMatcher(name, null);
	}

	public static TestEntityMatcher isTestEntity(String[] content) {
		return new TestEntityMatcher(null, content);
	}

	public static TestEntityMatcher isTestEntity(String name, String[] content) {
		return new TestEntityMatcher(name, content);
	}

	@Override
	public boolean matches(Object item) {
		if (!(item instanceof TestEntity))
			return false;

		TestEntity entity = (TestEntity) item;

		boolean result = true;
		if (name != null)
			result &= entity.name.equals(this.name);
		if (content != null)
			result &= entity.content.equals(this.content);

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
