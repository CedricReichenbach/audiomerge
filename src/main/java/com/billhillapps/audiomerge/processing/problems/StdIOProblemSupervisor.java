package com.billhillapps.audiomerge.processing.problems;

import java.util.Scanner;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.stdio.StdIOUtil;

public class StdIOProblemSupervisor implements ProblemSupervisor<CannotReadException> {

	@Override
	public boolean ignoreProblem(CannotReadException exception) {
		System.out.println("PROBLEM LOADING AN AUDIO FILE: " + exception.getMessage());
		System.out.println("How to proceed?\n- [s]kip this song\n- [g]ive up (and stop application)");

		Scanner scanner = StdIOUtil.getInputScanner();
		do {
			String input = scanner.nextLine();

			if (input.length() == 1) {
				char selection = Character.toLowerCase(input.charAt(0));
				switch (selection) {
				case 's':
					return true;
				case 'g':
					return false;
				}
			}

			System.out.println("I don't understand. Please type one of the characters s/g:");
		} while (true);
	}
}
