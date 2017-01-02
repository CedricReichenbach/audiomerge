package com.billhillapps.audiomerge.test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class TestUtil {

	public static void simulateStdInput(String firstLine, String... moreLines) {
		String input = buildLine(firstLine);
		Arrays.asList(moreLines).forEach(line -> buildLine(line));
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}

	private static String buildLine(String content) {
		return String.format("%s%n", content);
	}
}
