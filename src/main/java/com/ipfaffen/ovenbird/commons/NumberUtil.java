package com.ipfaffen.ovenbird.commons;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Isaias Pfaffenseller
 */
public class NumberUtil {
	private static final Locale DEFAULT_LOCALE = Locale.US;

	/**
	 * Format number based on the given mask.<br>
	 * Eg:<br>
	 * 1) #,##0.00<br>
	 * 2) #,###<br>
	 * 
	 * @param number
	 * @param mask
	 * @param locale
	 * @return
	 */
	public static String formatNumber(Number number, String mask, Locale locale) {
		if(number == null || mask == null) {
			return null;
		}

		if(locale == null) {
			locale = DEFAULT_LOCALE;
		}

		DecimalFormat formatter = new DecimalFormat(mask);
		formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(locale));

		try {
			return formatter.format(number);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Format number based on the given mask.<br>
	 * Ex:<br>
	 * 1) #,##0.00<br>
	 * 2) #,###<br>
	 * 
	 * @param number
	 * @param mask
	 * @return
	 */
	public static String formatNumber(Number number, String mask) {
		return formatNumber(number, mask, null);
	}

	/**
	 * Convert number string to number object based on the given mask.
	 * 
	 * @param number
	 * @param mask
	 * @return
	 */
	public static Number convertNumber(String number, String mask) {
		if(StringUtils.isBlank(number)) {
			return null;
		}

		DecimalFormat formatter = new DecimalFormat(mask);

		try {
			return formatter.parse(number);
		}
		catch(Exception e) {
			return null;
		}
	}
}