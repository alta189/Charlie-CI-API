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
 * Defubes a basic library
 */
public class BasicLibraryDefinition {
	/**
	 * Library name
	 */
	private String name;
	/**
	 * Library version
	 */
	private String version;
	/**
	 * Library url
	 */
	private String url;
	/**
	 * Library md5
	 */
	private String md5;

	/**
	 * Return the name of the library
	 *
	 * @return library's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the library
	 *
	 * @param name  name of the library
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the version of the library
	 *
	 * @return library's version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the name of the library
	 *
	 * @param version  version of the library
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns the url of the library
	 *
	 * @return library's url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url of the library
	 *
	 * @param url  url of the library
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the MD5 hash of the library
	 *
	 * @return library's md5 hash
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * Sets the MD5 hash of the library
	 *
	 * @param md5  md5 hash of the library
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * <p>
	 *     Returns the identifier of the library.
	 * </p>
	 * <p>
	 *     Same output as {@code getName() + ":" +  getVersion()}
	 * </p>
	 * @return
	 */
	public String getIdentifier() {
		return new StringBuilder(name).append(":").append(version).toString();
	}
}
