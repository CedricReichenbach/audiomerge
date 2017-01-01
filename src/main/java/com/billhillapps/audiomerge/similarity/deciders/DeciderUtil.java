package com.billhillapps.audiomerge.similarity.deciders;

import java.util.Scanner;

public class DeciderUtil {

	private static final Scanner SCANNER = new Scanner(System.in);

	public static int resolveUsingStdIn(Object leftOption, Object rightOption) {
		return resolveUsingStdIn(leftOption, rightOption, null);
	}

	public static int resolveUsingStdIn(Object leftOption, Object rightOption, Object defaultOption) {
		System.out
				.println(String.format(
						"SIMILARITY TO RESOLVE - OPTIONS:\n" + "[l]eft%s:\n\t%s\n" + "[r]ight%s:\n\t%s\n"
								+ "[k]eep both",
						leftOption == defaultOption ? ", [d]efault" : "", leftOption,
						rightOption == defaultOption ? ", [d]efault" : "", rightOption));

		do {
			// String input = SCANNER.next("^[LlRrKk]?$");
			String input = SCANNER.nextLine();

			if (input.length() == 0 || Character.toLowerCase(input.charAt(0)) == 'd') {
				if (defaultOption == leftOption)
					return -1;
				if (defaultOption == rightOption)
					return 1;
			} else if (input.length() == 1) {
				char selection = Character.toLowerCase(input.charAt(0));
				if (selection == 'l')
					return -1;
				if (selection == 'r')
					return 1;
				if (selection == 'k')
					return 0;
			}

			System.out.println("I don't understand. Please type one of the characters l/r/k:");
		} while (true);
	}
}
