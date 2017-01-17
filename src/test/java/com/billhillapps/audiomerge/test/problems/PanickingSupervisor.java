package com.billhillapps.audiomerge.test.problems;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class PanickingSupervisor implements ProblemSupervisor<CannotReadException> {

	@Override
	public boolean ignoreProblem(CannotReadException exception) {
		return false;
	}

}
