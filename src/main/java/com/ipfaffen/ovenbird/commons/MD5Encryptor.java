package com.ipfaffen.ovenbird.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Isaias Pfaffenseller
 */
public class MD5Encryptor {

	private static MessageDigest md = null;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param text
	 * @return
	 */
	private static char[] hexCodes(byte[] text) {
		char[] hexOutput = new char[text.length * 2];
		String hexString;

		for(int i = 0; i < text.length; i++) {
			hexString = "00" + Integer.toHexString(text[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);
		}
		return hexOutput;
	}

	/**
	 * @param text
	 * @return
	 */
	public static String encrypt(String text) {
		if(md != null) {
			return new String(hexCodes(md.digest(text.getBytes())));
		}
		return null;
	}
}