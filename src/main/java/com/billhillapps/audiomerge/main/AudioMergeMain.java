package com.billhillapps.audiomerge.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.billhillapps.audiomerge.processing.MergeManager;

public class AudioMergeMain {

	public static void main(String[] args) {
		// TODO: Args to enable GUI (instead of STDIO) etc.

		List<Path> paths = Arrays.asList(args).stream().map(Paths::get).collect(Collectors.toList());

		MergeManager mergeManager = new MergeManager(null, (Path[]) paths.toArray(new Path[] {}));
		mergeManager.execute();
		
		System.out.println("Merging finished, exiting now.");
	}
}
