package com.billhillapps.audiomerge.stdio;

import java.util.Scanner;

public class StdIOUtil {

	private static Scanner scanner;

	public static Scanner getInputScanner() {
		if (scanner == null)
			scanner = new Scanner(System.in);

		return scanner;
	}
}
