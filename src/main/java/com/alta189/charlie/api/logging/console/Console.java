/*
 * This file is part of Charlie CI API.
 *
 * Copyright (c) ${project.inceptionYear}, Stephen Williams (alta189) <http://charlie.alta189.com/>
 * Charlie CI API is licensed under the GNU Lesser General Public License.
 *
 * Charlie CI API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charlie CI API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.charlie.api.logging.console;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alta189.charlie.api.Charlie;
import com.alta189.charlie.api.Constants;
import com.alta189.charlie.api.command.CommandManager;
import com.alta189.charlie.api.command.CommonCommandManager;
import com.alta189.charlie.api.logging.Colors;
import com.alta189.charlie.api.logging.Log;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import org.fusesource.jansi.AnsiConsole;

public class Console {
	private final CommandManager commandManager = new CommonCommandManager(this);
	private final Log logger;
	private final RotatingFileHandler fileHandler;
	private final FancyConsoleHandler consoleHandler;
	private ConsoleReader reader;
	private ConsoleCommandThread thread;
	private boolean closed = false;
	private boolean running = true;

	private Console() {
		// Set up logger
		logger = Log.getLogger(Console.class.getCanonicalName());

		// Install color support if windows
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			AnsiConsole.systemInstall();
		}

		consoleHandler = new FancyConsoleHandler(this);

		String logFile = Charlie.getLogDirectory() + File.separator + Constants.Logging.LOG_FILE_FORMAT;
		if (new File(logFile).getParentFile() != null) {
			new File(logFile).getParentFile().mkdirs();
		}
		fileHandler = new RotatingFileHandler(logFile, this);

		consoleHandler.setFormatter(new DateOutputFormatter(new SimpleDateFormat(Constants.Logging.CONSOLE_DATE_FORMAT), true));
		fileHandler.setFormatter(new DateOutputFormatter(new SimpleDateFormat(Constants.Logging.LOG_ENTRY_DATE_FORMAT)));

		Logger logger = Logger.getLogger("");
		for (Handler h : logger.getHandlers()) {
			logger.removeHandler(h);
		}
		logger.addHandler(consoleHandler);
		logger.addHandler(fileHandler);
		logger.setFilter(new LoggerFilter());

		try {
			reader = new ConsoleReader();
		} catch (IOException ex) {
			getLogger().severe("Exception initializing console reader: {0}", ex.getMessage());
			ex.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownRunnable()));

		System.setOut(new PrintStream(new LoggerOutputStream(Level.INFO, this), true));
		System.setErr(new PrintStream(new LoggerOutputStream(Level.SEVERE, this), true));

		final Collection<Completer> completers = reader.getCompleters();
		for (Completer c : completers) {
			reader.removeCompleter(c);
		}

		LogFlushThread flushThread = new LogFlushThread(this);
		flushThread.start();
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void start() {
		thread = new ConsoleCommandThread();
		thread.setDaemon(true);
		thread.start();
	}
	public void stop() {
		running = false;
	}

	public void close() {
		if (!closed) {
			consoleHandler.flush();
			fileHandler.flush();
			fileHandler.close();
			closed = true;
		}
	}

	public void flush() {
		fileHandler.flush();
	}

	public ConsoleReader getReader() {
		return reader;
	}

	public Log getLogger() {
		return logger;
	}

	public boolean isRunning() {
		return running;
	}

	private class ConsoleCommandThread extends Thread {
		public ConsoleCommandThread() {
			super("Console");
		}

		@Override
		public void run() {
			String command;
			while (isRunning()) {
				try {
					command = reader.readLine(new StringBuilder().append("").append(Colors.RESET).append(">").toString(), null);
					if (command == null || command.trim().length() == 0) {
						continue;
					}

					commandManager.execute(command);
				} catch (Exception ex) {
					getLogger().severe("Impossible exception while executing command: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}

	private class ShutdownRunnable implements Runnable {
		@Override
		public void run() {
			if (isRunning()) {
				try {
					reader.print("\r");
					reader.flush();
					reader.killLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				stop();
				close();
			}
		}
	}

	private class LogFlushThread extends Thread {
		Console console;

		public LogFlushThread(Console manager) {
			super("Log Flush Thread");
			this.setDaemon(true);
			this.console = manager;
		}

		@Override
		public void run() {
			while (!this.isInterrupted()) {
				console.flush();
				try {
					sleep(60000);
				} catch (InterruptedException ignored) {
				}
			}
		}
	}
}
