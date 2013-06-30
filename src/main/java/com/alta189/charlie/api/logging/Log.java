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
package com.alta189.charlie.api.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log extends Logger {

	/**
	 * Protected method to construct a logger for a named subsystem.
	 * <p/>
	 * The logger will be initially configured with a null Level
	 * and with useParentHandlers set to true.
	 * @param name A name for the logger.  This should
	 * be a dot-separated name and should normally
	 * be based on the package name or class name
	 * of the subsystem, such as java.net
	 * or javax.swing.  It may be null for anonymous Loggers.
	 * @param resourceBundleName name of ResourceBundle to be used for localizing
	 * messages for this logger.  May be null if none
	 * of the messages require localization.
	 * @throws java.util.MissingResourceException if the resourceBundleName is non-null and
	 * no corresponding resource can be found.
	 */
	protected Log(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}

	public static Log getLogger(String name) {
		return new Log(name, null);
	}

	public void severe(String msg, Object param1) {
		log(Level.SEVERE, msg, param1);
	}

	public void severe(String msg, Object[] params) {
		log(Level.SEVERE, msg, params);
	}

	public void severe(String msg, Throwable throwable) {
		log(Level.SEVERE, msg, throwable);
	}

	public void warning(String msg, Object param1) {
		log(Level.WARNING, msg, param1);
	}

	public void warning(String msg, Object[] params) {
		log(Level.WARNING, msg, params);
	}

	public void warning(String msg, Throwable throwable) {
		log(Level.WARNING, msg, throwable);
	}

	public void info(String msg, Object param1) {
		log(Level.INFO, msg, param1);
	}

	public void info(String msg, Object[] params) {
		log(Level.INFO, msg, params);
	}

	public void info(String msg, Throwable throwable) {
		log(Level.INFO, msg, throwable);
	}

	public void debug(String msg) {
		log(LogLevel.DEBUG, msg);
	}

	public void debug(String msg, Object param1) {
		log(LogLevel.DEBUG, msg, param1);
	}

	public void debug(String msg, Object[] params) {
		log(LogLevel.DEBUG, msg, params);
	}

	public void debug(String msg, Throwable throwable) {
		log(LogLevel.DEBUG, msg, throwable);
	}

	public void config(String msg, Object param1) {
		log(Level.CONFIG, msg, param1);
	}

	public void config(String msg, Object[] params) {
		log(Level.CONFIG, msg, params);
	}

	public void config(String msg, Throwable throwable) {
		log(Level.CONFIG, msg, throwable);
	}

	public void fine(String msg, Object param1) {
		log(Level.FINE, msg, param1);
	}

	public void fine(String msg, Object[] params) {
		log(Level.FINE, msg, params);
	}

	public void fine(String msg, Throwable throwable) {
		log(Level.FINE, msg, throwable);
	}

	public void finer(String msg, Object param1) {
		log(Level.FINER, msg, param1);
	}

	public void finer(String msg, Object[] params) {
		log(Level.FINER, msg, params);
	}

	public void finer(String msg, Throwable throwable) {
		log(Level.FINER, msg, throwable);
	}

	public void finest(String msg, Object param1) {
		log(Level.FINEST, msg, param1);
	}

	public void finest(String msg, Object[] params) {
		log(Level.FINEST, msg, params);
	}

	public void finest(String msg, Throwable throwable) {
		log(Level.FINEST, msg, throwable);
	}
}
