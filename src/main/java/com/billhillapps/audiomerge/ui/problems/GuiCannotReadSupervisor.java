package com.billhillapps.audiomerge.ui.problems;

import java.util.concurrent.ExecutionException;

import org.jaudiotagger.audio.exceptions.CannotReadException;

import com.billhillapps.audiomerge.processing.problems.ProblemSupervisor;

public class GuiCannotReadSupervisor implements ProblemSupervisor<CannotReadException> {

	private final CannotReadPrompt prompt;

	public GuiCannotReadSupervisor(CannotReadPrompt prompt) {
		this.prompt = prompt;
	}

	@Override
	public boolean ignoreProblem(CannotReadException exception) {
		try {
			return prompt.promptForSkip(exception).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Interrupted while waiting for user supervision", e);
		}
	}

}
