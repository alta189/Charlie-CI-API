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
package com.alta189.charlie.api.library.definition;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BasicLibraryDefinitionFileTest {
	private static final String FILE_LOCATION = "/com/alta189/charlie/api/library/basiclib.xml";
	private String rawData;

	@BeforeTest
	public void setup() {
		InputStream testData = getClass().getResourceAsStream(FILE_LOCATION);

		Assert.assertNotNull(testData, "test data file not found");

		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(testData, writer, "UTF-8");
		} catch (IOException e) {
			Assert.fail("Error reading test data file", e);
		}
		rawData = writer.toString();
		Assert.assertNotNull(rawData);
	}

	@Test
	public void checkParsing() {
		BasicLibraryDefinitionFile defs = null;

		try {
			defs = new BasicDefinitionReader().loadXML(rawData).read();
		} catch (Exception e) {
			Assert.fail("Parse error", e);
		}
		Assert.assertNotNull(defs, "definition file parsing was unsuccessful");
		Assert.assertNotNull(defs.getDefinitions(), "definitions was null: " + rawData);
		Assert.assertEquals(defs.getDefinitions().size(), 1, "unexpected definitions size");

		BasicLibraryDefinition basicLib = defs.getDefinitions().get(0);
		Assert.assertNotNull(basicLib, "basic library definition was null");
		Assert.assertEquals(basicLib.getName(), "BasicTestLibrary", "incorrect name");
		Assert.assertEquals(basicLib.getVersion(), "1.5.6", "incorrect version");
		Assert.assertEquals(basicLib.getUrl(), "http://cdn.alta189.com/files/charlieci/test/basic_library_test.jar", "incorrect url");
		Assert.assertEquals(basicLib.getMd5(), "temp", "incorrect md5");
	}
}
