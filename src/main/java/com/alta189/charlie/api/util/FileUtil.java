package com.alta189.charlie.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.Validate;

/**
 * Class that adds utilities for interacting with files
 */
public class FileUtil {
	/**
	 * Gets the MD5 of the file
	 *
	 * @param file  may not be null, must exist
	 * @return file's md5 hash
	 * @throws IOException
	 */
	public static String getMD5(File file) throws IOException {
		Validate.notNull(file);
		if (!file.exists()) {
			throw new IllegalArgumentException("file did not exist");
		}
		FileInputStream fis = new FileInputStream(file);
		return DigestUtils.md5Hex(fis);
	}
}
