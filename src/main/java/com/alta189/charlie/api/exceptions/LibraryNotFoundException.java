package com.alta189.charlie.api.exceptions;

/**
 * Thrown when a LibraryProvider cannot find a library
 */
public class LibraryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7393511858757121861L;

	public LibraryNotFoundException(String message) {
		super(message);
	}

	public LibraryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
