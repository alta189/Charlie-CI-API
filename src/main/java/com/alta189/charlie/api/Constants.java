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
