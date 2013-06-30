package com.alta189.charlie.api.logging.console;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

import com.alta189.charlie.api.Charlie;
import com.alta189.charlie.api.logging.LogLevel;

public class LoggerFilter implements Filter {
	@Override
	public boolean isLoggable(LogRecord record) {
		if (record.getLevel() == LogLevel.DEBUG && !Charlie.isDebugMode()) {
			return false;
		} else if (record.getMessage() == null || record.getMessage().isEmpty()) {
			return false;
		}
		return true;
	}
}
