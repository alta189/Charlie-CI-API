package com.alta189.charlie.api;

import java.io.File;

import com.alta189.charlie.api.library.LibraryManager;
import com.alta189.charlie.api.logging.Log;

/**
 * <p>
 * The parent class for Charlie CI
 * </p>
 * <p>
 * You can access almost anything from the this class
 * </p>
 */
public class Charlie {
	private static ICharlie charlie;

	public static void registerIsntance(ICharlie instance) {
		if (charlie != null) {
			throw new IllegalAccessError("Instance has already been set");
		}
		charlie = instance;
	}

	public static Log getLogger() {
		return charlie.getLogger();
	}

	public static LibraryManager getLibraryManager() {
		return charlie.getLibraryManager();
	}

	public static File getLogDirectory() {
		return charlie.getLogDirectory();
	}

	public static boolean isDebugMode() {
		return charlie.isDebugMode();
	}
}
