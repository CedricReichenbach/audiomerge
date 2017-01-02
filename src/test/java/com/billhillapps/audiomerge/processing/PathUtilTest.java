package com.billhillapps.audiomerge.processing;

import static com.billhillapps.audiomerge.processing.PathUtil.sanitizePathElement;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PathUtilTest {

	@Test
	public void windowsIllegalCharactersAreReplaced() {
		assertEquals("___", sanitizePathElement("///"));
		assertEquals("foo_bar", sanitizePathElement("foo*bar"));
		assertEquals("_foo__bar", sanitizePathElement("\\foo:?bar"));
		assertEquals("foo.bar_test_", sanitizePathElement("foo.bar<test>"));
		assertEquals("foo_bar", sanitizePathElement("foo|bar"));
		assertEquals("foo_ _bar_.", sanitizePathElement("foo? \"bar\"."));

		assertEquals("test", sanitizePathElement("test"));
		assertEquals("This is a test.", sanitizePathElement("This is a test."));
		assertEquals("{Kanye} is a [West]!", sanitizePathElement("{Kanye} is a [West]!"));
	}

	@Test
	public void dotsReceiveUnderscore() {
		assertEquals("_.", sanitizePathElement("."));
		assertEquals("_..", sanitizePathElement(".."));
		assertEquals("_...", sanitizePathElement("..."));
	}
}
