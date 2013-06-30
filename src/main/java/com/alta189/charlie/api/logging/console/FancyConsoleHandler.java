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
package com.alta189.charlie.api.logging.console;

import java.io.IOException;
import java.util.logging.ConsoleHandler;

import com.alta189.charlie.api.logging.console.Console;

public class FancyConsoleHandler extends ConsoleHandler {
	private final Console console;

	public FancyConsoleHandler(Console console) {
		this.console = console;
	}

	@Override
	public synchronized void flush() {
		try {
			console.getReader().print("\r");
			console.getReader().flush();
			super.flush();
			try {
				console.getReader().drawLine();
			} catch (Throwable ex) {
				console.getReader().getCursorBuffer().clear();
			}
			console.getReader().flush();
		} catch (IOException ex) {
			console.getLogger().severe("I/O exception flushing console output", ex);
		}
	}
}