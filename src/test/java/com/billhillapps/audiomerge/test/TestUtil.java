package com.billhillapps.audiomerge.test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

public class TestUtil {

	public static void simulateStdInput(String firstLine, String... moreLines) {
		final StringBuilder input = new StringBuilder(buildLine(firstLine));
		Arrays.asList(moreLines).forEach(line -> input.append(buildLine(line)));
		System.setIn(new ByteArrayInputStream(input.toString().getBytes()));
	}

	private static String buildLine(String content) {
		return String.format("%s%n", content);
	}

	public static void runNTimes(int n, Runnable lambda) {
		IntStream.rangeClosed(1, n).forEach(i -> lambda.run());
	}
}
