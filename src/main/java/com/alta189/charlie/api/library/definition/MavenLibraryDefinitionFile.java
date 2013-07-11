package com.alta189.charlie.api.library.definition;

import java.util.List;

public class MavenLibraryDefinitionFile {
	private List<MavenLibraryDefinition> libraries;
	private List<MavenRepositoryDefinition> repositories;

	public List<MavenLibraryDefinition> getLibraries() {
		return libraries;
	}

	public void setLibraries(List<MavenLibraryDefinition> libraries) {
		this.libraries = libraries;
	}

	public List<MavenRepositoryDefinition> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<MavenRepositoryDefinition> repositories) {
		this.repositories = repositories;
	}
}
