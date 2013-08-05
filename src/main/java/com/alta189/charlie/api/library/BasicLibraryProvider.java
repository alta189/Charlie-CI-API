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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alta189.charlie.api.Constants;
import com.alta189.charlie.api.exceptions.LibraryNotFoundException;
import com.alta189.charlie.api.library.definition.BasicDefinitionReader;
import com.alta189.charlie.api.library.definition.BasicLibraryDefinition;
import com.alta189.charlie.api.library.definition.BasicLibraryDefinitionFile;
import com.alta189.charlie.api.util.FileUtil;
import com.alta189.charlie.api.util.VersionComparator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;

/**
 * Provider for basic libraries
 */
public class BasicLibraryProvider implements LibraryProvider<BasicLibrary> {
	/**
	 * Cache directory for basic libraries
	 */
	private final File root;
	/**
	 * Cache of libraries already loaded by the provider
	 */
	private final Map<String, BasicLibrary> libraries = new HashMap<String, BasicLibrary>();

	/**
	 * Constructs an instance of the provider based on its LibraryManager
	 *
	 * @param manager  LibraryManagor of the provider
	 */
	public BasicLibraryProvider(LibraryManager manager) {
		root = new File(manager.getCacheDirectory(), "basic");
		if (!root.exists()) {
			root.mkdirs();
		}
	}

	/**
	 * Returns the root directory of the local library cache
	 *
	 * @return root directory of local cache
	 */
	@Override
	public File getCacheRoot() {
		return root;
	}

	/**
	 * Returns an instance of a library based on its identifier {@see Library.getIdentifier()}
	 *
	 * @param identifier library's identifier, not null
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	@Override
	public BasicLibrary getLibrary(String identifier) throws LibraryNotFoundException {
		Validate.notNull(identifier);
		Validate.notEmpty(identifier);

		if (identifier.split(":").length < 2) {
			throw new LibraryNotFoundException(new StringBuilder("the identifier '").append(identifier).append("' is not valid").toString());
		}

		identifier = identifier.toLowerCase();

		BasicLibrary result = checkCache(identifier);

		if (result != null) {
			return result;
		}

		String[] ident = identifier.split(":");
		String name = ident[0];
		String version = ident[1];

		File libDir = new File(root, LibraryManager.removeSpecialChars(name));
		if (!libDir.exists()) {
			throw new LibraryNotFoundException(new StringBuilder("No versions of library: '").append(name).append("' have been provided").toString());
		} else if (!libDir.isDirectory()) {
			throw new LibraryNotFoundException(new StringBuilder("Expected file: '").append(libDir.getAbsolutePath()).append("' to be a directory, not a file").toString());
		}

		File libFile = new File(libDir, LibraryManager.removeSpecialChars(version) + BasicLibrary.EXTENSION);
		if (!libDir.exists()) {
			throw new LibraryNotFoundException(new StringBuilder("Version '").append(version).append("' of library '").append("' has not been provided").toString());
		} else if (libDir.isDirectory()) {
			throw new LibraryNotFoundException(new StringBuilder("Expected file: '").append(libDir.getAbsolutePath()).append("' to be a file, not a directory").toString());
		}
		result = new BasicLibrary(name, version, libFile);
		libraries.put(identifier.toLowerCase(), result);

		return result;
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
		Validate.notNull(name);
		Validate.notEmpty(name);

		File libDir = new File(root, LibraryManager.removeSpecialChars(name));
		if (!libDir.exists()) {
			throw new LibraryNotFoundException(new StringBuilder("No versions of library: '").append(name).append("' have been provided").toString());
		} else if (!libDir.isDirectory()) {
			throw new LibraryNotFoundException(new StringBuilder("Expected file: '").append(libDir.getAbsolutePath()).append("' to be a directory, not a file").toString());
		}

		List<String> versions = new ArrayList<String>();

		for (String f : libDir.list()) {
			if (f != null && !f.isEmpty()) {
				String ext = FilenameUtils.getExtension(f);
				if (ext != null && !ext.isEmpty() && ext.toLowerCase() == "jar") {
					versions.add(FilenameUtils.getBaseName(f));
				}
			}
		}

		if (versions.size() < 1) {
			throw new LibraryNotFoundException(new StringBuilder("No versions of library: '").append(name).append("' have been provided").toString());
		}

		Collections.sort(versions, new VersionComparator());

		File file = new File(libDir, LibraryManager.removeSpecialChars(versions.get(0)) + BasicLibrary.EXTENSION);
		if (file == null || !file.exists()) {
			throw new LibraryNotFoundException(new StringBuilder("No versions of library: '").append(name).append("' have been provided").toString());
		}

		String identifier = new StringBuilder(name).append(":").append(versions.get(0)).toString();

		BasicLibrary result = checkCache(identifier);

		if (result == null) {
			result = new BasicLibrary(name, versions.get(0), file);
			libraries.put(identifier.toLowerCase(), result);
		}

		return result;
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
		if (file == null || !file.exists()) {
			throw new LibraryNotFoundException("File was null or empty");
		}
		try {
			return getLibraries(FileUtils.readFileToString(file));
		} catch (IOException e) {
			throw new LibraryNotFoundException(new StringBuilder("Error when reading basic library definition file '").append(file.getName()).append("'").toString(), e);
		}
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
		List<BasicLibrary> result = new ArrayList<BasicLibrary>();

		BasicLibraryDefinitionFile defs = null;
		try {
			defs = new BasicDefinitionReader().loadXML(raw).read();
		} catch (Exception e) {
			throw new LibraryNotFoundException("Exception while reading basic definition file", e);
		}
		for (BasicLibraryDefinition def : defs.getDefinitions()) {
			BasicLibrary library = getLibrary(def);
			if (library == null) {
				throw new LibraryNotFoundException(new StringBuilder("Unable to find basic library '").append(def.getIdentifier()).append("'").toString());
			}

			result.add(library);
		}
		return null;
	}

	/**
	 * Returns a library based on a BasicLibraryDefinition
	 *
	 * @param definition defines a library, not null
	 * @return instance of library
	 */
	public BasicLibrary getLibrary(BasicLibraryDefinition definition) {
		Validate.notNull(definition);

		BasicLibrary library = null;

		try {
			library = getLibrary(definition.getIdentifier());
		} catch (Exception e) {
			// Ignored
		}

		if (library != null) {
			return library;
		}

		File libDir = new File(getCacheRoot(), LibraryManager.removeSpecialChars(definition.getName().toLowerCase()));
		if (!libDir.exists()) {
			libDir.mkdirs();
		}

		File libFile = new File(libDir, LibraryManager.removeSpecialChars(definition.getVersion()) + ".jar");

		try {
			FileUtils.copyURLToFile(new URL(definition.getUrl()), libFile, Constants.Download.CONNECT_TIMEOUT, Constants.Download.READ_TIMEOUT);

			if (definition.getMd5() != null && !definition.getMd5().isEmpty()) {
				String md5 = FileUtil.getMD5(libFile);
				if (md5 != definition.getMd5()) {
					throw new LibraryNotFoundException(new StringBuilder("Error when downloading basic library '").append(definition.getIdentifier()).append("': MD5 of file did not match").toString());
				}
			}
		} catch (LibraryNotFoundException e) {
			throw e; // Do not wrap a LibraryNotFoundException
		} catch (Exception e) {
			throw new LibraryNotFoundException(new StringBuilder("Error when downloading basic library '").append(definition.getIdentifier()).append("'").toString());
		}
		BasicLibrary result = new BasicLibrary(definition.getName(), definition.getVersion(), libFile);
		libraries.put(definition.getIdentifier().toLowerCase(), result);
		return result;
	}

	/**
	 * Checks the libraries that the provider has already provided
	 *
	 * @param identifier library's identifier, not null
	 * @return instance of library
	 */
	public BasicLibrary checkCache(String identifier) {
		Validate.notNull(identifier);
		Validate.notEmpty(identifier);
		identifier = identifier.toLowerCase();

		return libraries.get(identifier);
	}
}
