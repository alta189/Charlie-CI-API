package com.alta189.charlie.api.library.definition;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MavenDefinitionReader extends DefinitionReader<MavenLibraryDefinitionFile> {
	@Override
	public MavenLibraryDefinitionFile read() {
		MavenLibraryDefinitionFile result = new MavenLibraryDefinitionFile();
		List<MavenLibraryDefinition> defs = new ArrayList<MavenLibraryDefinition>();
		List<MavenRepositoryDefinition> repos = new ArrayList<MavenRepositoryDefinition>();

		if (super.doc.getElementsByTagName("dependencies").getLength() == 1) {
			NodeList dependencies = ((Element) super.doc.getElementsByTagName("dependencies").item(0)).getElementsByTagName("dependency");

			for (int i = 0; i < dependencies.getLength(); i++) {
				Node node = dependencies.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					MavenLibraryDefinition mld = new MavenLibraryDefinition();
					mld.setGroupId(element.getElementsByTagName("groupId").item(0).getTextContent());
					mld.setArtifactId(element.getElementsByTagName("artifactId").item(0).getTextContent());
					mld.setVersion(element.getElementsByTagName("version").item(0).getTextContent());

					defs.add(mld);
				}
			}
		}

		if (super.doc.getElementsByTagName("repositories").getLength() == 1) {
			NodeList repositories = ((Element) super.doc.getElementsByTagName("repositories").item(0)).getElementsByTagName("repository");

			for (int i = 0; i < repositories.getLength(); i++) {
				Node node = repositories.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					MavenRepositoryDefinition mrd = new MavenRepositoryDefinition();
					mrd.setId(element.getElementsByTagName("id").item(0).getTextContent());
					mrd.setUrl(element.getElementsByTagName("url").item(0).getTextContent());
					if (element.getElementsByTagName("type").getLength() > 0) {
						mrd.setType(element.getElementsByTagName("type").item(0).getTextContent());
					}

					repos.add(mrd);
				}
			}
		}

		result.setDefinitions(defs);
		result.setRepositories(repos);

		return result;
	}
}
