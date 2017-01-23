package com.billhillapps.audiomerge.test.problems;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.processing.problems.CannotReadFileProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class IgnoreAllSupervisor implements ProblemSupervisor<CannotReadFileProblem, CannotReadException> {

	@Override
	public boolean ignoreProblem(CannotReadFileProblem problem) {
		return true;
	}

}
