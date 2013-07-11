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
import java.util.List;

import com.alta189.charlie.api.exceptions.LibraryNotFoundException;

public class BasicLibraryProvider implements LibraryProvider<BasicLibrary> {
	/**
	 * Returns the root directory of the local library cache
	 *
	 * @return root directory of local cache
	 */
	@Override
	public File getCacheRoot() {
		return null;
	}

	/**
	 * Returns an instance of a library based on its identifier {@see Library.getIdentifier()}
	 *
	 * @param identifier libraries identifier, not null
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	@Override
	public BasicLibrary getLibrary(String identifier) throws LibraryNotFoundException {
		return null;
	}

	/**
	 * Returns the latest version of the library that the provider
	 * can find.
	 *
	 * @param name name of the library
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	@Override
	public BasicLibrary getLatestVersion(String name) throws LibraryNotFoundException {
		return null;
	}

	/**
	 * Reads a file and gets the libraries defined by the file
	 *
	 * @param file file that contains library definitions
	 * @return list of libraries
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if a library cannot be found
	 */
	@Override
	public List<BasicLibrary> getLibraries(File file) throws LibraryNotFoundException {
		return null;
	}

	/**
	 * Reads a string and gets the libraries defined by it
	 *
	 * @param raw string that contains library definitions
	 * @return list of libraries
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if a library cannot be found
	 */
	@Override
	public List<BasicLibrary> getLibraries(String raw) throws LibraryNotFoundException {
		return null;
	}
}
