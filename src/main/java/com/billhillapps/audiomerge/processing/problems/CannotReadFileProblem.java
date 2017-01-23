package com.billhillapps.audiomerge.processing.problems;

import java.nio.file.Path;

import org.jaudiotagger.audio.exceptions.CannotReadException;

/**
 * Implementation of {@link FileProblem} related to {@link CannotReadException}.
 * 
 * @author Cedric Reichenbach
 */
public class CannotReadFileProblem implements FileProblem<CannotReadException> {

	private final CannotReadException exception;
	private final Path path;

	public CannotReadFileProblem(CannotReadException exception, Path path) {
		this.exception = exception;
		this.path = path;
	}

	@Override
	public CannotReadException getException() {
		return exception;
	}

	@Override
	public Path getPath() {
		return path;
	}

}
