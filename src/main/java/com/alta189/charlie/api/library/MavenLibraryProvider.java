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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alta189.charlie.api.exceptions.LibraryNotFoundException;
import com.alta189.charlie.api.library.definition.MavenDefinitionReader;
import com.alta189.charlie.api.library.definition.MavenLibraryDefinition;
import com.alta189.charlie.api.library.definition.MavenLibraryDefinitionFile;
import com.alta189.charlie.api.library.maven.AetherModule;
import com.alta189.charlie.api.library.maven.MavenRepository;
import com.google.inject.Guice;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.eclipse.aether.version.Version;

public class MavenLibraryProvider implements LibraryProvider<MavenLibrary> {
	private final File cacheRoot;
	private final RepositorySystem repoSystem;
	private final RepositorySystemSession session;
	private final Map<String, RemoteRepository> repositories;
	private final Map<String, MavenLibrary> libraries;
	private final MavenRepository central;

	public MavenLibraryProvider(LibraryManager manager) {
		cacheRoot = new File(manager.getCacheDirectory(), "maven");

		repoSystem = newRepositorySystem();
		session = newSession(repoSystem);
		repositories = new HashMap<String, RemoteRepository>();
		libraries = new HashMap<String, MavenLibrary>();

		// Resolve Maven Central
		central = new MavenRepository("central", "default", "http://repo1.maven.org/maven2/");
		resolveRepository(central);
	}

	private RepositorySystem newRepositorySystem() {
		return Guice.createInjector(new AetherModule()).getInstance(RepositorySystem.class);
	}

	private RepositorySystemSession newSession(RepositorySystem system) {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

		LocalRepository localRepo = new LocalRepository(cacheRoot);
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

		return session;
	}

	/**
	 * Returns the root directory of the local library cache
	 *
	 * @return root directory of local cache
	 */
	@Override
	public File getCacheRoot() {
		return cacheRoot;
	}

	/**
	 * Returns an instance of a library based on its identifier {@see Library.getIdentifier()}
	 *
	 * @param identifier libraries identifier, not null
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	@Override
	public MavenLibrary getLibrary(String identifier) throws LibraryNotFoundException {
		return getLibrary(identifier, null);
	}

	/**
	 * Returns an instance of a library based on its identifier {@see Library.getIdentifier()}
	 *
	 * @param identifier libraries identifier, not null
	 * @param repositories maven repositories to search
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	public MavenLibrary getLibrary(String identifier, MavenRepository... repositories) throws LibraryNotFoundException {
		if (libraries.get(identifier) != null) {
			return libraries.get(identifier);
		}
		try {
			RepositorySystem repoSystem = newRepositorySystem();

			RepositorySystemSession session = newSession(repoSystem);

			Dependency dependency = new Dependency(new DefaultArtifact(identifier), "compile");
			RemoteRepository central = resolveRepository(this.central);

			CollectRequest collectRequest = new CollectRequest();
			collectRequest.setRoot(dependency);
			collectRequest.addRepository(central);

			if (repositories != null && repositories.length > 0) {
				for (MavenRepository mvnRepo : repositories) {
					RemoteRepository repository = resolveRepository(mvnRepo);
					if (repository != null) {
						collectRequest.addRepository(repository);
					}
				}
			}

			DependencyNode node = repoSystem.collectDependencies(session, collectRequest).getRoot();

			DependencyRequest dependencyRequest = new DependencyRequest();
			dependencyRequest.setRoot(node);

			repoSystem.resolveDependencies(session, dependencyRequest);

			Artifact artifact = node.getArtifact();

			if (artifact == null) {
				throw new NullPointerException("Artifact was null");
			}

			MavenLibrary library = new MavenLibrary(artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion(), artifact.getFile());

			libraries.put(identifier, library);

			return library;
		} catch (Exception e) {
			throw new LibraryNotFoundException(new StringBuilder().append("Could not find  maven library '").append(identifier).append("'").toString(), e);
		}
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
	public MavenLibrary getLatestVersion(String name) throws LibraryNotFoundException {
		return getLibrary(name, null);
	}

	/**
	 * Returns the latest version of the library that the provider
	 * can find.
	 *
	 * @param name name of the library
	 * @param repositories maven repositories to search
	 * @return instance of the library
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if the library cannot be found
	 */
	public MavenLibrary getLatestVersion(String name, MavenRepository... repositories) throws LibraryNotFoundException {
		Artifact artifact = new DefaultArtifact(name + ":[0,)");

		RemoteRepository repo = resolveRepository(central);

		VersionRangeRequest rangeRequest = new VersionRangeRequest();
		rangeRequest.setArtifact(artifact);
		rangeRequest.addRepository(repo);

		if (repositories != null && repositories.length > 0) {
			for (MavenRepository mvnRepo : repositories) {
				RemoteRepository repository = resolveRepository(mvnRepo);
				if (repository != null) {
					rangeRequest.addRepository(repository);
				}
			}
		}

		VersionRangeResult rangeResult = null;
		try {
			rangeResult = repoSystem.resolveVersionRange(session, rangeRequest);
		} catch (VersionRangeResolutionException e) {
			throw new LibraryNotFoundException(new StringBuilder().append("Could not find latest version of maven library '").append(name).append("'").toString(), e);
		}

		Version latest = rangeResult.getHighestVersion();
		return getLibrary(new StringBuilder().append(name).append(":").append(latest.toString()).toString());
	}

	/**
	 * <p>
	 * Reads a file and gets the libraries defined by the file
	 * </p>
	 * <br />
	 * <p>
	 * NOTE: Cannot handle POM.xml! {@see readPom()}
	 * </p>
	 *
	 * @param file file that contains library definitions
	 * @return list of libraries
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if a library cannot be found
	 */
	@Override
	public List<MavenLibrary> getLibraries(File file) throws LibraryNotFoundException {
		if (file == null || !file.exists()) {
			throw new LibraryNotFoundException("File was null or empty");
		}

		try {

			if (file.getName().toUpperCase().endsWith("pom.xml")) {
				return readPom(FileUtils.readFileToString(file));
			} else {
				return getLibraries(FileUtils.readFileToString(file));
			}
		} catch (IOException e) {
			throw new LibraryNotFoundException(new StringBuilder("Error when reading maven library definition file '").append(file.getName()).append("'").toString(), e);
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
	public List<MavenLibrary> getLibraries(String raw) throws LibraryNotFoundException {
		List<MavenLibrary> result = new ArrayList<MavenLibrary>();

		MavenLibraryDefinitionFile defs = null;
		try {
			defs = new MavenDefinitionReader().loadXML(raw).read();
		} catch (Exception e) {
			throw new LibraryNotFoundException("Exception while reading maven definition file", e);
		}
		MavenRepository[] repos = defs.getRepositories().toArray(new MavenRepository[0]);

		for (MavenLibraryDefinition def : defs.getDefinitions()) {
			MavenLibrary library = getLibrary(def.getIdentifier(), repos);
			if (library == null) {
				throw new LibraryNotFoundException(new StringBuilder("Unable to find maven library '").append(def.getIdentifier()).append("'").toString());
			}

			result.add(library);
		}
		return result;
	}

	/**
	 * <p>
	 * Special handling of a maven POM.XML file
	 * </p>
	 * <p>
	 * While we use the same structure for a regular maven library definition file,
	 * we want to do special handling of a pom file because it does not
	 * require that transitive dependencies, or dependencies of dependencies,
	 * to be listed while the maven definition file does
	 * </p>
	 *
	 * @param pomFile string contents of a pom.xml file, may not be null
	 * @return list of maven libraries
	 * @throws LibraryNotFoundException
	 */
	private List<MavenLibrary> readPom(String pomFile) throws LibraryNotFoundException {
		Validate.notNull(pomFile);

		List<MavenLibrary> result = new ArrayList<MavenLibrary>();
		Map<String, Artifact> artifacts = new HashMap<String, Artifact>();

		RepositorySystem repoSystem = newRepositorySystem();
		RepositorySystemSession session = newSession(repoSystem);

		MavenLibraryDefinitionFile defs = null;
		try {
			defs = new MavenDefinitionReader().loadXML(pomFile).read();
		} catch (Exception e) {
			throw new LibraryNotFoundException("Exception while reading POM.XML file", e);
		}
		MavenRepository[] repos = defs.getRepositories().toArray(new MavenRepository[0]);

		for (MavenLibraryDefinition def : defs.getDefinitions()) {
			// Process listed enchantments

			try {
				if (artifacts.containsKey(def.getIdentifier())) {
					continue;
				}

				Artifact parentArtifact = new DefaultArtifact(def.getIdentifier());
				DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE);

				RemoteRepository central = resolveRepository(this.central);

				CollectRequest collectRequest = new CollectRequest();
				collectRequest.setRoot(new Dependency(parentArtifact, JavaScopes.COMPILE));
				collectRequest.addRepository(central);

				if (repos != null && repos.length > 0) {
					for (MavenRepository mvnRepo : repos) {
						RemoteRepository repository = resolveRepository(mvnRepo);
						if (repository != null) {
							collectRequest.addRepository(repository);
						}
					}
				}

				DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, classpathFlter);

				List<ArtifactResult> artifactResults = repoSystem.resolveDependencies(session, dependencyRequest).getArtifactResults();

				for (ArtifactResult artifactResult : artifactResults) {
					Artifact artifact = artifactResult.getArtifact();
					String identifier = new StringBuilder(artifact.getGroupId()).append(":").append(artifact.getArtifactId()).append(":").append(artifact.getBaseVersion()).toString();

					if (artifact.getFile() == null || !artifact.getFile().exists()) {
						throw new LibraryNotFoundException(new StringBuilder().append("Download of  maven library '").append(def.getIdentifier()).append("' was unsuccessful").toString());
					}

					if (!artifacts.containsKey(identifier)) {
						artifacts.put(identifier, artifact);
					}
				}
			} catch (LibraryNotFoundException e) {
				throw e; // We dont want to wrap LibraryNotFoundException in a LibraryNotFoundException
			} catch (Exception e) {
				throw new LibraryNotFoundException(new StringBuilder().append("Could not find maven library '").append(def.getIdentifier()).append("'").toString(), e);
			}
		}

		for (String identifier : artifacts.keySet()) {
			Artifact artifact = artifacts.get(identifier);
			MavenLibrary library = new MavenLibrary(artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion(), artifact.getFile());

			if (libraries.containsKey(identifier)) {
				libraries.put(identifier, library);
			}

			result.add(library);
		}

		return result;
	}

	/**
	 * Resolves a maven repository
	 *
	 * @param mavenRepository information used to resolve repository, not null
	 * @return aether remote repository
	 */
	public RemoteRepository resolveRepository(MavenRepository mavenRepository) {
		Validate.notNull(mavenRepository);
		return resolveRepository(mavenRepository.getId(), mavenRepository.getType(), mavenRepository.getUrl());
	}

	/**
	 * Resolves a maven repository and caches in the repositories map
	 *
	 * @param id repository id, must be unique and not null
	 * @param type repository type, may be null
	 * @param url repository url, not null
	 * @return aether remote repository
	 */
	public RemoteRepository resolveRepository(String id, String type, String url) {
		Validate.notNull(id);
		Validate.notNull(url);

		if (repositories.containsKey(id) && repositories.get(id) != null) {
			return repositories.get(id);
		}

		RemoteRepository repository = new RemoteRepository.Builder(id, type, url).build();

		if (repository != null) {
			repositories.put(id, repository);
		}

		return repository;
	}
}
