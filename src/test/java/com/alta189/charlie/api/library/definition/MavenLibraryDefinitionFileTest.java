package com.alta189.charlie.api.library.definition;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MavenLibraryDefinitionFileTest {
	private static final String FILE_LOCATION = "/com/alta189/charlie/api/library/mavenlib.xml";
	private static final String POM_FILE_LOCATION = "/com/alta189/charlie/api/library/pom.xml";
	private String rawData;
	private String rawPomData;

	@BeforeTest
	public void setup() {
		rawData = setup(FILE_LOCATION);
		rawPomData = setup(POM_FILE_LOCATION);
	}

	public String setup(String file) {
		InputStream testData = getClass().getResourceAsStream(file);

		Assert.assertNotNull(testData, "test data file not found");

		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(testData, writer, "UTF-8");
		} catch (IOException e) {
			Assert.fail("Error reading test data file", e);
		}
		Assert.assertNotNull(writer.toString());
		return writer.toString();
	}

	@Test
	public void checkParsing() {
		MavenLibraryDefinitionFile file = null;
		try {
			file = new MavenDefinitionReader().loadXML(rawData).read();
		} catch (Exception e) {
			Assert.fail("Parse error", e);
		}

		Assert.assertNotNull(file, "definition file parsing was unsuccessful");
		Assert.assertNotNull(file.getDefinitions(), "definitions was null");
		Assert.assertEquals(file.getDefinitions().size(), 1, "unexpected definitions size");

		MavenLibraryDefinition mavenLib = file.getDefinitions().get(0);
		Assert.assertNotNull(mavenLib, "maven library definition was null");
		Assert.assertEquals(mavenLib.getGroupId(), "com.alta189.test", "incorrect group id");
		Assert.assertEquals(mavenLib.getArtifactId(), "definition", "incorrect artifact id");
		Assert.assertEquals(mavenLib.getVersion(), "1.0.0", "incorrect version");

		MavenRepositoryDefinition repo = file.getRepositories().get(0);
		Assert.assertNotNull(repo, "maven repository definition was null");
		Assert.assertEquals(repo.getId(), "alta-nexus", "incorrect repository id");
		Assert.assertEquals(repo.getUrl(), "http://nexus.alta189.com/content/groups/public/", "incorrect repository url");
		Assert.assertEquals(repo.getType(), "release", "repo type was not null");

	}

	@Test
	public void checkPomParsing() {
		MavenLibraryDefinitionFile file = null;
		try {
			file = new MavenDefinitionReader().loadXML(rawPomData).read();
		} catch (Exception e) {
			Assert.fail("Parse error", e);
		}

		Assert.assertNotNull(file, "definition file parsing was unsuccessful");
		Assert.assertNotNull(file.getDefinitions(), "definitions was null");
		Assert.assertEquals(file.getDefinitions().size(), 1, "unexpected definitions size");

		MavenLibraryDefinition mavenLib = file.getDefinitions().get(0);
		Assert.assertNotNull(mavenLib, "maven library definition was null");
		Assert.assertEquals(mavenLib.getGroupId(), "com.alta189.test", "incorrect group id");
		Assert.assertEquals(mavenLib.getArtifactId(), "definition", "incorrect artifact id");
		Assert.assertEquals(mavenLib.getVersion(), "1.0.0", "incorrect version");

		MavenRepositoryDefinition repo = file.getRepositories().get(0);
		Assert.assertNotNull(repo, "maven repository definition was null");
		Assert.assertEquals(repo.getId(), "alta-nexus", "incorrect repository id");
		Assert.assertEquals(repo.getUrl(), "http://nexus.alta189.com/content/groups/public/", "incorrect repository url");
		Assert.assertNull(repo.getType(), "repo type was not null");

	}
}
