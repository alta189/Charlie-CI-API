package com.alta189.charlie.api.logging.console;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import com.alta189.charlie.api.Constants;

public class RotatingFileHandler extends StreamHandler {
	private final SimpleDateFormat date;
	private final String logFile;
	private final Console console;
	private String filename;

	public RotatingFileHandler(String logFile, Console console) {
		this.logFile = logFile;
		this.console = console;
		date = new SimpleDateFormat(Constants.Logging.LOG_FILE_DATE_FORMAT);
		filename = calculateFilename();
		try {
			setOutputStream(new FileOutputStream(filename, true));
		} catch (FileNotFoundException ex) {
			console.getLogger().severe("Unable to open {0} for writing: {1}", new Object[]{filename, ex.getMessage()});
			ex.printStackTrace();
		}
	}

	@Override
	public synchronized void flush() {
		if (!filename.equals(calculateFilename())) {
			filename = calculateFilename();
			console.getLogger().info("Log rotating to {0}...", filename);
			try {
				setOutputStream(new FileOutputStream(filename, true));
			} catch (FileNotFoundException ex) {
				console.getLogger().severe("Unable to open {0} for writing: {1}", new Object[]{filename, ex.getMessage()});
				ex.printStackTrace();
			}
		}
		super.flush();
	}

	@Override
	public synchronized void publish(LogRecord record) {
		super.publish(record);
	}

	private String calculateFilename() {
		return logFile.replace("%D", date.format(new Date()));
	}
}