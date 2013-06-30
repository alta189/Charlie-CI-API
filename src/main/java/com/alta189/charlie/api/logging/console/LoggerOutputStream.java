package com.alta189.charlie.api.logging.console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class LoggerOutputStream extends ByteArrayOutputStream {
	private final String separator = System.getProperty("line.separator");
	private final Level level;
	private final Console console;
	private String prefix;

	public LoggerOutputStream(Level level, Console console) {
		super();
		this.level = level;
		this.console = console;
	}

	public LoggerOutputStream(Level level, Console console, String prefix) {
		super();
		this.level = level;
		this.console = console;
		setPrefix(prefix);
	}

	@Override
	public synchronized void flush() throws IOException {
		StringBuilder record = new StringBuilder();
		super.flush();
		record.append(toString());
		super.reset();

		if (record.length() > 0 && !record.toString().equals(separator)) {
			if (prefix != null && !prefix.isEmpty()) {
				record.insert(0, prefix);
			}
			console.getLogger().logp(level, "LoggerOutputStream", "log" + level, record.toString());
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix + " ";
	}
}