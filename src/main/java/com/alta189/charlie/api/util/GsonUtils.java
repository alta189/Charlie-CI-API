package com.alta189.charlie.api.util;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class GsonUtils {
	private static final XmlParserCreator parserCreator;

	static {
		parserCreator = new XmlParserCreator() {
			@Override
			public XmlPullParser createParser() {
				try {
					return XmlPullParserFactory.newInstance().newPullParser();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public static GsonXml getGsonXmlInstance() {
		return new GsonXmlBuilder().setXmlParserCreator(parserCreator).create();
	}
}
