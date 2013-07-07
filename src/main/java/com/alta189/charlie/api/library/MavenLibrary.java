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

public class MavenLibrary implements Library {
	/**
	 * Library group id
	 */
	private final String groupId;
	/**
	 * Library artifact id
	 */
	private final String artifactId;
	/**
	 * Library version
	 */
	private final String version;
	/**
	 * Library cache
	 */
	private final File file;

	protected MavenLibrary(String groupId, String artifactId, String version, File file) {
		this.version = version;
		this.file = file;
		this.artifactId = artifactId;
		this.groupId = groupId;
	}

	/**
	 * <p>
	 *     Returns the name of the library
	 * </p>
	 * <p>
	 *     The name is defined by groupId:artifactId
	 * </p>
	 *
	 * @return name
	 */
	@Override
	public String getName() {
		return new StringBuilder().append(groupId).append(":").append(artifactId).toString();
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
	 * <p>
	 * Returns locally cached file of library
	 * </p>
	 * <p>
	 * File should exist
	 * </p>
	 *
	 * @return file
	 */
	@Override
	public File getFile() {
		return file;
	}

	/**
	 * <p>
	 *     Returns a string that is unique to this library,
	 *	   generally will be the same output as {@code getName() + ":" +  getVersion()}
	 * </p>
	 * <p>
	 *     Maven libraries identifiers are "groupId:artifactId:version"
	 * </p>
	 * @return indentifier
	 */
	@Override
	public String getIdentifier() {
		return new StringBuilder().append(groupId).append(":").append(artifactId).append(":").append(version).toString();
	}
}
