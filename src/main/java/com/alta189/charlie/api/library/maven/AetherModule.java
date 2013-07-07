package com.alta189.charlie.api.library.maven;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import org.apache.maven.repository.internal.MavenAetherModule;
import org.eclipse.aether.connector.file.FileRepositoryConnectorFactory;
import org.eclipse.aether.connector.wagon.WagonConfigurator;
import org.eclipse.aether.connector.wagon.WagonProvider;
import org.eclipse.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;

public class AetherModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new MavenAetherModule());

		bind(RepositoryConnectorFactory.class).annotatedWith(Names.named("file")).to(FileRepositoryConnectorFactory.class);
		bind(RepositoryConnectorFactory.class).annotatedWith(Names.named("wagon")).to(WagonRepositoryConnectorFactory.class);
		bind(WagonProvider.class).to(ManualWagonProvider.class);
		bind(WagonConfigurator.class).to(ManualWagonConfigurator.class);
	}

	@Provides
	@Singleton
	Set<RepositoryConnectorFactory> provideRepositoryConnectorFactories(@Named("file") RepositoryConnectorFactory file, @Named("wagon") RepositoryConnectorFactory wagon) {
		Set<RepositoryConnectorFactory> factories = new HashSet<RepositoryConnectorFactory>();
		factories.add(file);
		factories.add(wagon);
		return Collections.unmodifiableSet(factories);
	}
}
