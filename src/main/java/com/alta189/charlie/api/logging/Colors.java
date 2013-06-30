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
package com.alta189.charlie.api.logging;

import org.fusesource.jansi.Ansi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Colors {
	BLACK(Ansi.ansi().fg(Ansi.Color.BLACK).boldOff().toString()),
	BLUE(Ansi.ansi().fg(Ansi.Color.BLUE).bold().toString()),
	CYAN(Ansi.ansi().fg(Ansi.Color.CYAN).bold().toString()),
	DARK_BLUE(Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString()),
	DARK_CYAN(Ansi.ansi().fg(Ansi.Color.CYAN).boldOff().toString()),
	DARK_GRAY(Ansi.ansi().fg(Ansi.Color.BLACK).bold().toString()),
	DARK_GREEN(Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString()),
	DARK_MAGENTA(Ansi.ansi().fg(Ansi.Color.MAGENTA).boldOff().toString()),
	DARK_RED(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString()),
	GREEN(Ansi.ansi().fg(Ansi.Color.GREEN).bold().toString()),
	GOLD(Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff().toString()),
	MAGENTA(Ansi.ansi().fg(Ansi.Color.MAGENTA).bold().toString()),
	RED(Ansi.ansi().fg(Ansi.Color.RED).bold().toString()),
	RESET(Ansi.ansi().fg(Ansi.Color.DEFAULT).a(Ansi.Attribute.RESET).toString()),
	WHITE(Ansi.ansi().fg(Ansi.Color.WHITE).bold().toString()),
	YELLOW(Ansi.ansi().fg(Ansi.Color.YELLOW).bold().toString());

	private final String value;
	private final Pattern pattern;

	private Colors(String value) {
		this.value = value;
		pattern = Pattern.compile("\\{\\{" + this.name() + "\\}\\}");
	}

	@Override
	public String toString() {
		return value;
	}

	public static String parse(String input) {
		for (Colors color : values()) {
			Matcher matcher = color.pattern.matcher(input);
			if (matcher.find()) {
				input = matcher.replaceAll(color.value);
			}
		}
		return input;
	}

	public static String strip(String input) {
		for (Colors color : values()) {
			Matcher matcher = color.pattern.matcher(input);
			if (matcher.find()) {
				matcher.replaceAll("");
			}
			input = input.replaceAll(Pattern.quote(color.value), "");
		}
		return input;
	}
}
