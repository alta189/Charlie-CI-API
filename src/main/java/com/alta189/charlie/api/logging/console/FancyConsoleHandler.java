package com.alta189.charlie.api.logging.console;

import java.io.IOException;
import java.util.logging.ConsoleHandler;

import com.alta189.charlie.api.logging.console.Console;

public class FancyConsoleHandler extends ConsoleHandler {
	private final Console console;

	public FancyConsoleHandler(Console console) {
		this.console = console;
	}

	@Override
	public synchronized void flush() {
		try {
			console.getReader().print("\r");
			console.getReader().flush();
			super.flush();
			try {
				console.getReader().drawLine();
			} catch (Throwable ex) {
				console.getReader().getCursorBuffer().clear();
			}
			console.getReader().flush();
		} catch (IOException ex) {
			console.getLogger().severe("I/O exception flushing console output", ex);
		}
	}
}