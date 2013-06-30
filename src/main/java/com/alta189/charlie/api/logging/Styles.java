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
