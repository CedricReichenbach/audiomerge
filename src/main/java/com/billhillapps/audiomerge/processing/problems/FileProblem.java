package com.billhillapps.audiomerge.processing.problems;

import java.nio.file.Path;

public interface FileProblem<E extends Exception> extends Problem<E> {

	public Path getPath();
}
