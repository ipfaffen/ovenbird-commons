package com.ipfaffen.ovenbird.commons;

/**
 * @author Isaias Pfaffenseller
 */
public class StringUtil {

	/**
	 * Concat characters to string placing the separator before if the string is not empty.
	 * 
	 * @param string
	 * @param appendix
	 * @param separator
	 * @return
	 */
	public static String concatTo(String string, String appendix, String separator) {
		return string.concat((string.length() > 0 ? separator : "")).concat(appendix);
	}

	/**
	 * Concat characters to string placing the separator '.' before if the string is not empty.
	 * 
	 * @param string
	 * @param appendix
	 * @return
	 */
	public static String concatTo(String string, String appendix) {
		return concatTo(string, appendix, ".");
	}

	/**
	 * Append characters to string builder placing the separator before if the string builder is not empty.
	 * 
	 * @param sb
	 * @param appendix
	 * @param separator
	 */
	public static void appendTo(StringBuilder sb, CharSequence appendix, String separator) {
		sb.append((sb.length() > 0 ? separator : "")).append(appendix);
	}

	/**
	 * Append characters to string builder placing the separator ', ' before if the string builder is not empty.
	 * 
	 * @param sb
	 * @param appendix
	 */
	public static void appendTo(StringBuilder sb, CharSequence appendix) {
		appendTo(sb, appendix, ", ");
	}
}