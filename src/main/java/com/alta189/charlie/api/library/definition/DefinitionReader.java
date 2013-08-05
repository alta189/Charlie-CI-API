package com.alta189.charlie.api.library.definition;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Abstract definition reader
 * @param <T> Definitions file type it returns
 */
public abstract class DefinitionReader<T> {
	protected Document doc;

	public <E extends DefinitionReader<T>> E loadXML(String raw) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(raw)));

		doc.getDocumentElement().normalize();

		this.doc = doc;
		return (E) this;
	}

	public abstract T read();
}
