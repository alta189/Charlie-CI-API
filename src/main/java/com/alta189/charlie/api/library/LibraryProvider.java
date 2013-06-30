package com.alta189.charlie.api.library;

import java.io.File;
import java.util.List;

import com.alta189.charlie.api.exceptions.LibraryNotFoundException;

/**
 * Class that provides Libraries to the LibraryManager
 *
 * @param &lt;T&gt; type of library that the provider serves
 * */
public interface LibraryProvider<T extends Library> {
	/**
	 * Returns the root directory of the local library cache
	 *
	 *
	 * @return root directory of local cache
	 */
	public File getCacheRoot();

	/**
	 * Returns an instance of a library based on its identifier {@see Library.getIdentifier()}
	 *
	 * @param identifier  libraries identifier, not null
	 * @return instance of the library
	 * @throws LibraryNotFoundException thrown if the library cannot be found
	 */
	public T getLibrary(String identifier) throws LibraryNotFoundException;

	/**
	 * Returns the latest version of the library that the provider
	 * can find.
	 *
	 * @param name  name of the library
	 * @return instance of the library
	 * @throws LibraryNotFoundException thrown if the library cannot be found
	 */
	public T getLatestVersion(String name) throws LibraryNotFoundException;

	/**
	 * Reads a file and gets the libraries defined by the file
	 *
	 * @param file  file that contains library definitions
	 * @return list of libraries
	 * @throws LibraryNotFoundException thrown if a library cannot be found
	 */
	public List<T> getLibraries(File file) throws LibraryNotFoundException;

	/**
	 * Reads a string and gets the libraries defined by it
	 *
	 * @param raw  string that contains library definitions
	 * @return list of libraries
	 * @throws LibraryNotFoundException thrown if a library cannot be found
	 */
	public List<T> getLibraries(String raw) throws LibraryNotFoundException;
}
