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

/**
 * Defines a maven repository
 */
public class MavenRepositoryDefinition {
	/**
	 * Maven reposotory id, must be unique
	 */
	private String id;
	/**
	 * Maven repository type, may be null
	 */
	private String type;
	/**
	 * Maven repository url
	 */
	private String url;

	/**
	 * Returns the id of the repository
	 *
	 * @return id of the repository
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of the repository
	 *
	 * @param id  repository's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the type of the repository
	 *
	 * @return maven repository type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the repository
	 *
	 * @param type  maven repository type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the url of the repository
	 *
	 * @return maven repository url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url of the repository
	 *
	 * @param url  maven repository url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
