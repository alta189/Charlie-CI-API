/*
 * This file is part of Charlie CI API.
 *
 * Copyright (c) 2013, Stephen Williams (alta189) <http://charlie.alta189.com/>
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.alta189.charlie.api.logging.Colors;
import com.alta189.charlie.api.logging.LogLevel;
import com.alta189.charlie.api.logging.Styles;

public class DateOutputFormatter extends Formatter {
	private final SimpleDateFormat date;
	private final boolean colored;

	public DateOutputFormatter(SimpleDateFormat date) {
		this(date, false);
	}

	public DateOutputFormatter(SimpleDateFormat date, boolean colored) {
		this.date = date;
		this.colored = colored;
	}

	@Override
	public String format(LogRecord record) {
		Level level = record.getLevel();
		StringBuilder builder = new StringBuilder();
		if (colored) {
			builder.append(Colors.RESET);
		}
		if (level == LogLevel.SPECIAL) {
			builder.append(formatMessage(record));
		} else {
			builder.append(date.format(record.getMillis()))
					.append(" [");
			if (colored) {
				if (level == Level.SEVERE) {
					builder.append(Colors.RED);
				} else if (level == Level.INFO) {
					builder.append(Colors.GREEN);
				} else if (level == Level.WARNING) {
					builder.append(Colors.GOLD);
				} else if (level == LogLevel.DEBUG) {
					builder.append(Colors.BLUE);
				}
			}
			builder.append(level.getLocalizedName().toUpperCase())
					.append(Colors.RESET)
					.append("] ")
					.append(formatMessage(record))
					.append('\n');

			if (record.getThrown() != null) {
				StringWriter writer = new StringWriter();
				record.getThrown().printStackTrace(new PrintWriter(writer));
				builder.append(writer.toString());
			}
		}

		if (!colored) {
			return Colors.strip(Styles.strip(builder.toString()));
		}
		return builder.toString();
	}
}