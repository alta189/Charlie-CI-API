package com.alta189.charlie.api.logging;

import java.util.logging.Level;

public class LogLevel extends Level {
	public static final Level DEBUG = new LogLevel("DEBUG", 750);
	public static final Level SPECIAL = new LogLevel("SPECIAL", INFO.intValue() + 1);

	protected LogLevel(String name, int value) {
		super(name, value);
	}
}
