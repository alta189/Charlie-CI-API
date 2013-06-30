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