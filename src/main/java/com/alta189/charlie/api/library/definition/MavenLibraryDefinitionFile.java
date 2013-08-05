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

import java.util.List;

/**
 * Definition file for maven libraries
 */
public class MavenLibraryDefinitionFile {
	/**
	 * List of definitions
	 */
	private List<MavenLibraryDefinition> definitions;
	/**
	 * Kist of maven repositories
	 */
	private List<MavenRepositoryDefinition> repositories;

	/**
	 * Returns list of definitions
	 *
	 * @return definitions list
	 */
	public List<MavenLibraryDefinition> getDefinitions() {
		return definitions;
	}

	/**
	 * Sets the list of definitions
	 *
	 * @param definitions list of definitions
	 */
	public void setDefinitions(List<MavenLibraryDefinition> definitions) {
		this.definitions = definitions;
	}

	/**
	 * Returns the list of maven repositories
	 *
	 * @return repository list
	 */
	public List<MavenRepositoryDefinition> getRepositories() {
		return repositories;
	}

	/**
	 * Sets the list of maven repositories
	 *
	 * @param repositories  list of repositories
	 */
	public void setRepositories(List<MavenRepositoryDefinition> repositories) {
		this.repositories = repositories;
	}
}
