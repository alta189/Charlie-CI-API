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
