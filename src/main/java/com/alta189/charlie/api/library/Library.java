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
