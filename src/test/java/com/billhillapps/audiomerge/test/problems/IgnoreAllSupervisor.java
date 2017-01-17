package com.billhillapps.audiomerge.test.problems;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class IgnoreAllSupervisor implements ProblemSupervisor<CannotReadException> {

	@Override
	public boolean ignoreProblem(CannotReadException exception) {
		return true;
	}

}
