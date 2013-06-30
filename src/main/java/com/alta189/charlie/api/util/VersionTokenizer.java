package com.alta189.charlie.api.util;

/**
 * Tokenizer used by {@link VersionComparator}
 *
 * <br />
 * Original source link: http://stackoverflow.com/a/10034633
 */
public class VersionTokenizer {
	private final String versionString;
	private final int length;
	private int pos;
	private int number;
	private String suffix;

	public VersionTokenizer(String versionString) {
		if (versionString == null) {
			throw new IllegalArgumentException("versionString is null");
		}

		this.versionString = versionString;
		length = versionString.length();
	}

	public int getNumber() {
		return number;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean next() {
		number = 0;
		suffix = "";

		// No more characters
		if (pos >= length) {
			return false;
		}

		while (pos < length) {
			char c = versionString.charAt(pos);
			if (c < '0' || c > '9') {
				break;
			}
			number = number * 10 + (c - '0');
			pos++;
		}

		int suffixStart = pos;

		while (pos < length) {
			char c = versionString.charAt(pos);
			if (c == '.') {
				break;
			}
			pos++;
		}

		suffix = versionString.substring(suffixStart, pos);

		if (pos < length) {
			pos++;
		}

		return true;
	}
}
