package com.billhillapps.audiomerge.processing;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class PathUtil {

	/**
	 * Create a subpath (subdirectory or file) below a given path.
	 * 
	 * The provided element name is sanitized first if necessary.
	 * 
	 * @param path
	 *            Path to create new element below
	 * @param subElement
	 *            Name representing single path segment (slashes will be
	 *            replaced)
	 * @return Resulting path with new subelement
	 */
	public static Path createSafeSubpath(final Path path, final String subElement) {
		// remove slashes, as subElement just represents a single segment
		String halfSafeElement = subElement.replaceAll("[\\\\/]", "_");

		// - leading dots may hide items in some systems
		// - trailing dots are ignored in windows
		//  => replace both to avoid side effects
		halfSafeElement = halfSafeElement.replaceAll("^\\.+|\\.+$", "_");

		Path subPath;
		try {
			// try without sanitizing first, because the user's FS might
			// be less restrictive
			subPath = path.resolve(halfSafeElement);
		} catch (InvalidPathException e) {
			subPath = path.resolve(PathUtil.sanitizePathElement(halfSafeElement));
		}

		return subPath;
	}

	/**
	 * Remove special (illegal) character from path segment to make it comply to
	 * common file system restrictions.
	 * 
	 * In particular, replace anything matching [\\/:*?\"<>|] by an underscore
	 * (_) each. In addition, prepend paths only consisting of dots (.) with one
	 * underscore (_).
	 */
	public static String sanitizePathElement(final String element) {
		String sanitized = element;

		if (sanitized.chars().allMatch(charInt -> charInt == '.'))
			sanitized = "_" + sanitized;

		return sanitized.replaceAll("[\\\\/:*?\"<>|]", "_");
	}
}
