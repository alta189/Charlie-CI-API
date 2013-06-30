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
