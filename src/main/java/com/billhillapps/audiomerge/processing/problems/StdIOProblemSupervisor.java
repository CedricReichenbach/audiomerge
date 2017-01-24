package com.billhillapps.audiomerge.processing.problems;

import java.util.Scanner;

import com.billhillapps.audiomerge.stdio.StdIOUtil;

public class StdIOProblemSupervisor implements ProblemSupervisor<AudioLoadingProblem> {

	@Override
	public boolean ignoreProblem(AudioLoadingProblem problem) {
		System.out.println("PROBLEM LOADING AN AUDIO FILE: " + problem.getException().getMessage());
		System.out.println("PATH: " + problem.getPath());
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
