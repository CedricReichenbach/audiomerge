package com.billhillapps.audiomerge.processing.problems;

/**
 * Interface for reviewer objects deciding whether a problem should be ignored
 * and the program continued, or the operation should be aborted (possibly
 * exiting the whole application). <br>
 * For example, trying to load a corrupt song file may trigger an exception. An
 * implemenation of this interface could now prompt the user how to proceed,
 * i.e. whether to continue without this song or give up the whole merging.
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 *            Exception type indicating this problem
 * @param <E>
 */
public interface ProblemSupervisor<P extends Problem<E>, E extends Exception> {

	public boolean ignoreProblem(P problem);
}
