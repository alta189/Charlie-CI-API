package com.alta189.charlie.api.util.download;

import java.io.File;

public interface DownloadListener {
	public void stateChanged(File download, float progress);
}
