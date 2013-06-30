package com.alta189.charlie.api.command;

public class CommandException extends Exception {
	private static final long serialVersionUID = 7936404856385100186L;

	public CommandException(String msg) {
		super(msg);
	}

	public CommandException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}
}
