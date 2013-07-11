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
package com.alta189.charlie.api.logging;

import org.fusesource.jansi.Ansi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Styles {
	BLINK_FAST(Ansi.ansi().a(Ansi.Attribute.BLINK_FAST).toString()),
	BLINK_OFF(Ansi.ansi().a(Ansi.Attribute.BLINK_OFF).toString()),
	BLINK_SLOW(Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString()),
	CONCEAL_OFF(Ansi.ansi().a(Ansi.Attribute.CONCEAL_OFF).toString()),
	CONCEAL_ON(Ansi.ansi().a(Ansi.Attribute.CONCEAL_ON).toString()),
	INTENSITY_BOLD(Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD).toString()),
	INTENSITY_BOLD_OFF(Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD_OFF).toString()),
	INTENSITY_FAINT(Ansi.ansi().a(Ansi.Attribute.INTENSITY_FAINT).toString()),
	ITALIC(Ansi.ansi().a(Ansi.Attribute.ITALIC).toString()),
	ITALIC_OFF(Ansi.ansi().a(Ansi.Attribute.ITALIC_OFF).toString()),
	NEGATIVE_OFF(Ansi.ansi().a(Ansi.Attribute.NEGATIVE_OFF).toString()),
	NEGATIVE_ON(Ansi.ansi().a(Ansi.Attribute.NEGATIVE_ON).toString()),
	RESET(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.DEFAULT).toString()),
	STRIKETHROUGH_OFF(Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_OFF).toString()),
	STRIKETHROUGH_ON(Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString()),
	UNDERLINE(Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString()),
	UNDERLINE_DOUBLE(Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString()),
	UNDERLINE_OFF(Ansi.ansi().a(Ansi.Attribute.UNDERLINE_OFF).toString());

	private final String value;
	private final Pattern pattern;

	private Styles(String value) {
		this.value = value;
		pattern = Pattern.compile("\\{\\{" + name() + "\\}\\}");
	}

	@Override
	public String toString() {
		return value;
	}

	public static String parse(String input) {
		for (Styles style : values()) {
			Matcher matcher = style.pattern.matcher(input);
			if (matcher.find()) {
				input = matcher.replaceAll(style.value);
			}
		}
		return input;
	}

	public static String strip(String input) {
		for (Styles style : values()) {
			Matcher matcher = style.pattern.matcher(input);
			if (matcher.find()) {
				matcher.replaceAll("");
			}
			input = input.replaceAll(Pattern.quote(style.value), "");
		}
		return input;
	}
}
