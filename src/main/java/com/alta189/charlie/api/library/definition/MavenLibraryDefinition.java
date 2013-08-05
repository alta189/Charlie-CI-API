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
package com.alta189.charlie.api.library.definition;

public class MavenLibraryDefinition {
	/**
	 * Library's group id
	 */
	private String groupId;
	/**
	 * Library's artifact id
	 */
	private String artifactId;
	/**
	 * Library's version
	 */
	private String version;

	/**
	 * Returns the maven group id
	 *
	 * @return groupdId of the library
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Sets the maven group id
	 *
	 * @param groupId  library's groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Returns the maven artifact id
	 *
	 * @return artifactId of the library
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Sets the maven artifact id
	 *
	 * @param artifactId  library's artifactId
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Returns the maven version
	 *
	 * @return version of the library
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the maven version
	 *
	 * @param version  library's version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * <p>
	 *     Returns the identifier of the library.
	 * </p>
	 * <p>
	 *     Same output as {@code getGroupId() + ":" +  getArtifactId() + ":" +  getVersion()}
	 * </p>
	 * @return
	 */
	public String getIdentifier() {
		return new StringBuilder(groupId).append(":").append(artifactId).append(":").append(version).toString();
	}
}
