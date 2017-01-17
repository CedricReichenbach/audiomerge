package com.billhillapps.audiomerge.processing;

/**
 * Exception indicating that loading a music collection failed.
 * 
 * @author Cedric Reichenbach
 */
public class LoadingFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoadingFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
