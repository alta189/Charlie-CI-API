package com.alta189.charlie.api.library.maven;

import org.apache.maven.wagon.Wagon;
import org.eclipse.aether.connector.wagon.WagonConfigurator;

public class ManualWagonConfigurator implements WagonConfigurator {
	public void configure(Wagon wagon, Object configuration) throws Exception {
	}
}