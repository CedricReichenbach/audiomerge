package com.billhillapps.audiomerge.processing.problems;

/**
 * Wrapper for exceptions due to failure of loading meta data of an audio file.
 * 
 * This class mainly exists because exceptions in
 * org.jaudiotagger.audio.exceptions have no proper inheritance tree.
 * 
 * @author Cedric Reichenbach
 */
public class AudioLoadingException extends Exception {

	private static final long serialVersionUID = 1L;

	public AudioLoadingException(Exception cause) {
		super(cause.getMessage(), cause);
	}
}
