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
	 * Library identifier
	 */
	private final String identifier;

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
	public BasicLibrary(String name, String version, String url, String md5) {
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
		file = new File(Charlie.getLibraryManager().getDirectory(BasicLibrary.class), path.toString());

		identifier = new StringBuilder(name).append(":").append(version).toString();
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
	public String getVersion() {
		return version;
	}

	/**
	 * Returns the name of the library
	 *
	 * @return name
	 */
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
		return identifier;
	}
}
