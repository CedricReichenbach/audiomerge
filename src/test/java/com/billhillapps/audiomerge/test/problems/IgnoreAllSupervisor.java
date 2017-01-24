package com.billhillapps.audiomerge.test.problems;

import com.billhillapps.audiomerge.processing.problems.AudioLoadingProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class IgnoreAllSupervisor implements ProblemSupervisor<AudioLoadingProblem> {

	@Override
	public boolean ignoreProblem(AudioLoadingProblem problem) {
		return true;
	}
}
