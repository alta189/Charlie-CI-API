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

import java.util.Comparator;

/**
 * Compares two version strings
 *
 * <br />
 * Original source link: http://stackoverflow.com/a/10034633
 */
public class VersionComparator implements Comparator {

	/**
	 * Checks if the two provided versions are equal
	 *
	 * @param o1  first version,
	 * @param o2  second version
	 * @return true if versions are equal
	 */
	public boolean equals(Object o1, Object o2) {
		return compare(o1, o2) == 0;
	}

	/**
	 * Compares two version strings
	 *
	 * @param o1 first version string
	 * @param o2 second version string
	 * @return the comparator value, negative if less, positive if greater
	 */
	public int compare(Object o1, Object o2) {
		String version1 = (String) o1;
		String version2 = (String) o2;

		VersionTokenizer tokenizer1 = new VersionTokenizer(version1);
		VersionTokenizer tokenizer2 = new VersionTokenizer(version2);

		int number1 = 0, number2 = 0;
		String suffix1 = "", suffix2 = "";

		while (tokenizer1.next()) {
			if (!tokenizer2.next()) {
				do {
					number1 = tokenizer1.getNumber();
					suffix1 = tokenizer1.getSuffix();
					if (number1 != 0 || suffix1.length() != 0) {
						// Version one is longer than number two, and non-zero
						return 1;
					}
				}
				while (tokenizer1.next());

				// Version one is longer than version two, but zero
				return 0;
			}

			number1 = tokenizer1.getNumber();
			suffix1 = tokenizer1.getSuffix();
			number2 = tokenizer2.getNumber();
			suffix2 = tokenizer2.getSuffix();

			if (number1 < number2) {
				// Number one is less than number two
				return -1;
			}
			if (number1 > number2) {
				// Number one is greater than number two
				return 1;
			}

			boolean empty1 = suffix1.length() == 0;
			boolean empty2 = suffix2.length() == 0;

			if (empty1 && empty2) {
				continue; // No suffixes
			}
			if (empty1) {
				return 1; // First suffix is empty (1.2 > 1.2b)
			}
			if (empty2) {
				return -1; // Second suffix is empty (1.2a < 1.2)
			}

			// Lexical comparison of suffixes
			int result = suffix1.compareTo(suffix2);
			if (result != 0) {
				return result;
			}
		}
		if (tokenizer2.next()) {
			do {
				number2 = tokenizer2.getNumber();
				suffix2 = tokenizer2.getSuffix();
				if (number2 != 0 || suffix2.length() != 0) {
					// Version one is longer than version two, and non-zero
					return -1;
				}
			}
			while (tokenizer2.next());

			// Version two is longer than version one, but zero
			return 0;
		}
		return 0;
	}
}
