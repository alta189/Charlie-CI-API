package com.alta189.charlie.api.library.definition;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Reads a basic definition file
 */
public class BasicDefinitionReader extends DefinitionReader<BasicLibraryDefinitionFile> {
	/**
	 * Parses the xml String
	 *
	 * @param raw  raw xml String
	 * @return this object
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Override
	public BasicDefinitionReader loadXML(String raw) throws ParserConfigurationException, IOException, SAXException {
		return super.loadXML(raw);
	}

	/**
	 * Parses the XML document and coverts it to a BasicLibraryDefinitionFile object
	 *
	 * @return definition file
	 */
	@Override
	public BasicLibraryDefinitionFile read() {
		NodeList definitions = super.doc.getElementsByTagName("definition");
		BasicLibraryDefinitionFile result = new BasicLibraryDefinitionFile();
		List<BasicLibraryDefinition> defs = new ArrayList<BasicLibraryDefinition>();

		for (int i = 0; i < definitions.getLength(); i++) {
			Node node = definitions.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element def = (Element) node;

				BasicLibraryDefinition bsl = new BasicLibraryDefinition();
				bsl.setName(def.getElementsByTagName("name").item(0).getTextContent());
				bsl.setVersion(def.getElementsByTagName("version").item(0).getTextContent());
				bsl.setUrl(def.getElementsByTagName("url").item(0).getTextContent());
				bsl.setMd5(def.getElementsByTagName("md5").item(0).getTextContent());

				defs.add(bsl);
			}
		}

		result.setDefinitions(defs);
		return result;
	}
}
