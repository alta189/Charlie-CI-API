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
import java.util.List;

import com.alta189.charlie.api.exceptions.LibraryNotFoundException;
import com.alta189.charlie.api.library.maven.AetherModule;
import com.google.inject.Guice;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;

public class MavenLibraryProvider implements LibraryProvider<MavenLibrary> {
	private final File cacheRoot;
	private final RepositorySystem repoSystem;
	private final RepositorySystemSession session;

	public MavenLibraryProvider(LibraryManager manager) {
		cacheRoot = new File(manager.getCacheDirectory(), "maven");

		repoSystem = newRepositorySystem();
		session = newSession(repoSystem);
	}

	public RemoteRepository newCentralRepository() {
		return new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/").build();
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
		try {
			RepositorySystem repoSystem = newRepositorySystem();

			RepositorySystemSession session = newSession(repoSystem);

			Dependency dependency = new Dependency(new DefaultArtifact("org.apache.maven:maven-profile:2.2.1"), "compile");
			RemoteRepository central = new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/").build();

			CollectRequest collectRequest = new CollectRequest();
			collectRequest.setRoot(dependency);
			collectRequest.addRepository(central);
			DependencyNode node = repoSystem.collectDependencies(session, collectRequest).getRoot();

			DependencyRequest dependencyRequest = new DependencyRequest();
			dependencyRequest.setRoot(node);

			repoSystem.resolveDependencies(session, dependencyRequest);

			Artifact artifact = node.getArtifact();

			if (artifact == null) {
				throw new NullPointerException("Artifact was null");
			}

			return new MavenLibrary(artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion(), artifact.getFile());
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
		Artifact artifact = new DefaultArtifact(name + ":[0,)");

		RemoteRepository repo = newCentralRepository();

		VersionRangeRequest rangeRequest = new VersionRangeRequest();
		rangeRequest.setArtifact(artifact);
		rangeRequest.addRepository(repo);

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
	 * Reads a file and gets the libraries defined by the file
	 *
	 * @param file file that contains library definitions
	 * @return list of libraries
	 * @throws com.alta189.charlie.api.exceptions.LibraryNotFoundException thrown if a library cannot be found
	 */
	@Override
	public List<MavenLibrary> getLibraries(File file) throws LibraryNotFoundException {
		return null;
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

		return null;
	}
}
