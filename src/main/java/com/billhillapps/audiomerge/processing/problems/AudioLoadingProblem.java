package com.billhillapps.audiomerge.processing.problems;

import java.nio.file.Path;

/**
 * Implementation of {@link FileProblem} related to
 * {@link AudioLoadingException}.
 * 
 * @author Cedric Reichenbach
 */
public class AudioLoadingProblem implements FileProblem<AudioLoadingException> {

	private final AudioLoadingException exception;
	private final Path path;

	public AudioLoadingProblem(AudioLoadingException exception, Path path) {
		this.exception = exception;
		this.path = path;
	}

	@Override
	public AudioLoadingException getException() {
		return exception;
	}

	@Override
	public Path getPath() {
		return path;
	}

}
