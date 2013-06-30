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
