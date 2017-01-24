package com.billhillapps.audiomerge.ui.problems;

import java.util.concurrent.ExecutionException;

import com.billhillapps.audiomerge.processing.problems.AudioLoadingProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class GuiCannotReadSupervisor implements ProblemSupervisor<AudioLoadingProblem> {

	private final CannotReadPrompt prompt;

	public GuiCannotReadSupervisor(CannotReadPrompt prompt) {
		this.prompt = prompt;
	}

	@Override
	public boolean ignoreProblem(AudioLoadingProblem problem) {
		try {
			return prompt.promptForSkip(problem).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Interrupted while waiting for user supervision", e);
		}
	}

}
