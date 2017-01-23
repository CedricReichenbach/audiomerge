package com.billhillapps.audiomerge.ui.problems;

import java.util.concurrent.ExecutionException;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.processing.problems.CannotReadFileProblem;
import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class GuiCannotReadSupervisor implements ProblemSupervisor<CannotReadFileProblem, CannotReadException> {

	private final CannotReadPrompt prompt;

	public GuiCannotReadSupervisor(CannotReadPrompt prompt) {
		this.prompt = prompt;
	}

	@Override
	public boolean ignoreProblem(CannotReadFileProblem problem) {
		try {
			return prompt.promptForSkip(problem).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Interrupted while waiting for user supervision", e);
		}
	}

}
