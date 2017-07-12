package com.ipfaffen.ovenbird.commons;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Isaias Pfaffenseller
 */
public class DateUtil {
	
	private static final Locale DEFAULT_LOCALE = Locale.US;

	/**
	 * Format date in millis based on the given mask.<br>
	 * Eg:<br>
	 * 1) dd/MM/yyyy<br>
	 * 2) dd/MM/yyyy HH:mm<br>
	 * 3) HH:mm
	 * 
	 * @param date
	 * @param mask
	 * @param locale
	 * @return
	 */
	public static String formatDate(Long date, String mask, Locale locale) {
		if(date == null || mask == null) {
			return null;
		}

		if(locale == null) {
			locale = DEFAULT_LOCALE;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(mask, locale);

		try {
			return formatter.format(date);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Format date in millis based on the given mask.<br>
	 * Eg:<br>
	 * 1) dd/MM/yyyy<br>
	 * 2) dd/MM/yyyy HH:mm<br>
	 * 3) HH:mm
	 * 
	 * @param date
	 * @param mask
	 * @return
	 */
	public static String formatDate(Long date, String mask) {
		return formatDate(date, mask, null);
	}

	/**
	 * Format date based on the given mask.<br>
	 * Eg:<br>
	 * 1) dd/MM/yyyy<br>
	 * 2) dd/MM/yyyy HH:mm<br>
	 * 3) HH:mm
	 * 
	 * @param date
	 * @param mask
	 * @param locale
	 * @return
	 */
	public static String formatDate(Date date, String mask, Locale locale) {
		if(date == null) {
			return null;
		}
		return formatDate(date.getTime(), mask, locale);
	}

	/**
	 * Format date based on the given mask.<br>
	 * Eg:<br>
	 * 1) dd/MM/yyyy<br>
	 * 2) dd/MM/yyyy HH:mm<br>
	 * 3) HH:mm
	 * 
	 * @param date
	 * @param mask
	 * @return
	 */
	public static String formatDate(Date date, String mask) {
		return formatDate(date, mask, null);
	}

	/**
	 * Convert date string to date object based on the given mask.
	 * 
	 * @param date
	 * @param mask
	 * @return
	 */
	public static Date convertDate(String date, String mask) {
		if(StringUtils.isBlank(date)) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(mask);

		try {
			return formatter.parse(date);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Create a new instance of current date.
	 * 
	 * @return
	 */
	public static java.sql.Date current() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * Create a new instance of timestamp with current date.
	 * 
	 * @return
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Create a new instance of time with current date.
	 * 
	 * @return
	 */
	public static Time currentTime() {
		return new Time(System.currentTimeMillis());
	}
}