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
package com.alta189.charlie.api;

public class Constants {

	public static class Logging {
		public static final String LOG_FILE_FORMAT = "log-%D.txt";
		public static final String LOG_FILE_DATE_FORMAT = "yyyy-MM-dd";
		public static final String CONSOLE_DATE_FORMAT = "HH:mm:ss";
		public static final String LOG_ENTRY_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	}

	public static class Download {
		public static final int CONNECT_TIMEOUT = 10000;
		public static final int READ_TIMEOUT = 10000;
		public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19";
	}
}
