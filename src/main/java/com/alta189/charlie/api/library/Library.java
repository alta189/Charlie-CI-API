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
package com.alta189.charlie.api.library;

import java.io.File;

public interface Library {
	/**
	 * Returns a string that is unique to this library,
	 * generally will be the same output as {@code getName() + ":" +  getVersion()}
	 *
	 * @return indentifier
	 */
	public String getIdentifier();

	/**
	 * Returns the name of the library
	 *
	 * @return name
	 */
	public String getName();

	/**
	 * Returns the version string of the library
	 *
	 * @return version
	 */
	public String getVersion();

	/**
	 * <p>
	 *     Returns locally cached file of library
	 * </p>
	 * <p>
	 *     File should exist
	 * </p>
	 * @return file
	 */
	public File getFile();
}
