package com.billhillapps.audiomerge.processing.problems;

/**
 * General interface for problems occurring during processing, caused by an Exception.
 * 
 * @author Cedric Reichenbach
 */
public interface Problem<T extends Exception> {

	public T getException();
}
