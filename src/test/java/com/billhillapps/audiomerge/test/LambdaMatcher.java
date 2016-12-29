package com.billhillapps.audiomerge.test;

import java.util.function.Function;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class LambdaMatcher<T> extends BaseMatcher<T> {

	private final Function<T, Boolean> matcherFunction;

	private LambdaMatcher(Function<T, Boolean> function) {
		super();
		this.matcherFunction = function;
	}

	public static final <T> LambdaMatcher<T> matches(Function<T, Boolean> function) {
		return new LambdaMatcher<>(function);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean matches(Object item) {
		return matcherFunction.apply((T) item);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("some object approved by lambda ").appendValue(matcherFunction);
	}

}
