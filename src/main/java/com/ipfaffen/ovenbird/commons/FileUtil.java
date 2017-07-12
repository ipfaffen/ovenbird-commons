package com.ipfaffen.ovenbird.commons;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Isaias Pfaffenseller
 */
public class FileUtil {

	/**
	 * @param file
	 * @return
	 */
	public static String generateUniqueFilename(File file) {
		return generateUniqueFilename(FilenameUtils.getExtension(file.getName()));
	}

	/**
	 * @param file
	 * @return
	 */
	public static String generateUniqueFilename(String extension) {
		StringBuilder sb = new StringBuilder();
		sb.append(RandomUtil.generateUUID64());
		sb.append(".");
		sb.append(extension.toLowerCase());
		return sb.toString();
	}

	/**
	 * @param file
	 * @param newExtension
	 * @return
	 */
	public static String replaceExtension(String file, String newExtension) {
		return FilenameUtils.getName(file).concat(".").concat(newExtension);
	}
}