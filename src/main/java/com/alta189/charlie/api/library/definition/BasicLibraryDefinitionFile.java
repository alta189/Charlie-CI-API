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

import com.google.gson.annotations.SerializedName;

/**
 * Definition file for basic libraries
 */
public class BasicLibraryDefinitionFile {
	/**
	 * List of definitions in the definition file
	 */
	@SerializedName("definitions")
	private List<BasicLibraryDefinition> definitions;

	/**
	 * Returns list of definitions
	 *
	 * @return definitions
	 */
	public List<BasicLibraryDefinition> getDefinitions() {
		return definitions;
	}

	/**
	 * Sets the list of definitions
	 *
	 * @param definitions  list of definitions
	 */
	public void setDefinitions(List<BasicLibraryDefinition> definitions) {
		this.definitions = definitions;
	}
}
