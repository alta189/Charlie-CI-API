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
package com.alta189.charlie.api.library;

import java.io.File;

import com.alta189.charlie.api.Charlie;

/**
 * <p>
 *     Represents a Library that can be loaded into the classloader
 * </p>
 * <p>
 *     Only jar files are accepted
 * </p>
 *
 */
public class BasicLibrary implements Library {
	/**
	 * Jar file extension constant
	 */
	private static final String EXTENSION = ".jar";
	/**
	 * Library name
	 */
	private final String name;
	/**
	 * Library version
	 */
	private final String version;
	/**
	 * Library URL
	 */
	private final String url;
	/**
	 * MD5 hash of the file
	 */
	private final String md5;
	/**
	 * Library cache
	 */
	private final File file;

	/**
	 * <p>
	 *     Creates a Library object with the given info
	 * </p>
	 * <p>
	 *     Will set up the local cache file of the library, the file may not exist
	 * </p>
	 *
	 * @param name  the name of the library, not null
	 * @param version  the version of the library, not null
	 * @param url  the url of the library, not null
	 * @param md5  the md5 of the library, if null md5 checks will not be performed
	 */
	protected BasicLibrary(String name, String version, String url, String md5) {
		if (name == null || name.isEmpty()) {
			throw new NullPointerException("name cannot be null or empty");
		}
		if (version == null || version.isEmpty()) {
			throw new NullPointerException("version cannot be null or empty");
		}
		if (url == null || url.isEmpty()) {
			throw new NullPointerException("url cannot be null or empty");
		}
		if (md5 == null || md5.isEmpty()) {
			Charlie.getLogger().debug("library " + name + " md5 is empty, md5 checks will not be performed");
		}

		this.name = name.toLowerCase();
		this.version = version;
		this.url = url;
		this.md5 = md5;

		StringBuilder path = new StringBuilder().append(removeSpecialChars(name)).append(File.separator).append(removeSpecialChars(version)).append(EXTENSION);
		file = new File(Charlie.getLibraryManager().getProvider(BasicLibrary.class).getCacheRoot(), path.toString());
	}

	/**
	 * Replaces special characters with an underscore
	 *
	 * @param str  string to remove special characters from
	 * @return processed string
	 */
	private String removeSpecialChars(String str) {
		return str.replaceAll("[^a-zA-Z0-9.-]", "_");
	}

	/**
	 * <p>
	 *     Returns locally cached file of library
	 * </p>
	 * <p>
	 *     File may not exist
	 * </p>
	 * @return file
	 */
	@Override
	public File getFile() {
		return file;
	}

	/**
	 * Returns url of library
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Returns the version string of the library
	 *
	 * @return version
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * Returns the name of the library
	 *
	 * @return name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 *     Returns the MD5 hash of the library
	 * </p>
	 * <p>
	 *     Should be checked against the local cache of the library
	 * </p>
	 *
	 * @return md5 hash
	 */
	public String getMD5() {
		return md5;
	}

	/**
	 * Returns a string that is unique to this library,
	 * generally will be the same output as {@code getName() + ":" +  getVersion()}
	 *
	 * @return indentifier
	 */
	@Override
	public String getIdentifier() {
		return new StringBuilder(name).append(":").append(version).toString();
	}
}
