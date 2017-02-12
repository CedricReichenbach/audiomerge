package com.billhillapps.audiomerge.similarity.deciders;

import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

public class DistanceUtil {

	public static final double JARO_WINKLER_THRESHOLD = 0.91;

	public static boolean areSimilar(String a, String b) {
		return similarity(a, b) >= JARO_WINKLER_THRESHOLD;
	}

	/**
	 * Compute the Jaro-Winkler distance between two strings after mapping them
	 * to lowercase ascii-only characters.
	 * 
	 * @return double in [0, 1], indicating similarity. 0 means no similarity, 1
	 *         means equality.
	 */
	public static double similarity(String a, String b) {
		return StringUtils.getJaroWinklerDistance(simplify(a), simplify(b));
	}

	/**
	 * Transform string to ASCII lower case equivalents and remove special
	 * characters like punctuation, i.e. only keep alpha-numerics and space.
	 */
	private static String simplify(String string) {
		return toLowerAscii(string).replaceAll("[^\\w\\s]", "");
	}

	private static String toLowerAscii(String string) {
		return Normalizer.normalize(string.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
}
