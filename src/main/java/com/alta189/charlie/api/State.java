package com.alta189.charlie.api;

/**
 * Represents the current state of the server
 */
public enum State {
	/**
	 * State when the server is starting up
	 */
	STARTUP,

	/**
	 * State when the server is shutting down
	 */
	SHUTDOWN,

	/**
	 * State when the server is building a project
	 */
	BUILDING,

	/**
	 * State when the server is not currently building a project
	 */
	IDLE
}
