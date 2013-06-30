package com.alta189.charlie.api;

import java.io.File;

import com.alta189.charlie.api.library.LibraryManager;
import com.alta189.charlie.api.logging.Log;

/**
 * Interface for the parent class
 */
public interface ICharlie {
	public State getState();

	public boolean isMaster();

	public boolean isSlave();

	public LibraryManager getLibraryManager();

	public Log getLogger();

	public File getLogDirectory();

	public boolean isDebugMode();
}
