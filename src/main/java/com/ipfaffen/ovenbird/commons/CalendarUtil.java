package com.ipfaffen.ovenbird.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Isaias Pfaffenseller
 */
public class CalendarUtil {
	/**
	 * Calendar with current date but time fields setted to first hour (<b>HOUR_OF_DAY</b> = 0; <b>MINUTE</b> = 0; <b>SECOND</b> = 0).
	 */
	public static final Calendar CALENDAR_FIRST_HOUR;

	/**
	 * Calendar with current date but time fields setted to last hour(<b>HOUR_OF_DAY</b> = 23; <b>MINUTE</b> = 59; <b>SECOND</b> = 59).
	 */
	public static final Calendar CALENDAR_LAST_HOUR;

	static {
		CALENDAR_FIRST_HOUR = Calendar.getInstance();
		CALENDAR_FIRST_HOUR.set(Calendar.HOUR_OF_DAY, 0);
		CALENDAR_FIRST_HOUR.set(Calendar.MINUTE, 0);
		CALENDAR_FIRST_HOUR.set(Calendar.SECOND, 0);

		CALENDAR_LAST_HOUR = Calendar.getInstance();
		CALENDAR_LAST_HOUR.set(Calendar.HOUR_OF_DAY, 23);
		CALENDAR_LAST_HOUR.set(Calendar.MINUTE, 59);
		CALENDAR_LAST_HOUR.set(Calendar.SECOND, 59);
	}

	/**
	 * To be used in the compare and createCalendar method call.<br>
	 * Compare <b>HOUR_OF_DAY</b>, <b>MINUTE</b> and <b>SECOND</b>.
	 */
	public static final int[] TIME = new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND};

	/**
	 * To be used in the compare and createCalendar method call.<br>
	 * Compare <b>HOUR_OF_DAY</b> and <b>MINUTE</b>.
	 */
	public static final int[] HOUR_AND_MINUTE = new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE};

	/**
	 * To be used in the compare and createCalendar method call.<br>
	 * Compare <b>YEAR</b>, <b>MONTH</b> AND <b>DAY_OF_MONTH</b>.
	 */
	public static final int[] DATE = new int[]{Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH};

	/**
	 * To be used in the difference method call.<br>
	 * Returns the difference of the dates <b>in milliseconds</b>.
	 */
	public static final int IN_MILLISECONDS = 1;

	/**
	 * To be used in the difference method call.<br>
	 * Returns the difference of the dates <b>in seconds</b>.
	 */
	public static final int IN_SECONDS = 1000;

	/**
	 * To be used in the difference method call.<br>
	 * Returns the difference of the dates <b>in minutes</b>.
	 */
	public static final int IN_MINUTES = IN_SECONDS * 60;

	/**
	 * To be used in the difference method call.<br>
	 * Returns the difference of the dates <b>in hours</b>.
	 */
	public static final int IN_HOURS = IN_MINUTES * 60;

	/**
	 * To be used in the difference method call.<br>
	 * Returns the difference of the dates <b>in days</b>.
	 */
	public static final int IN_DAYS = IN_HOURS * 24;

	/**
	 * Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules.<br>
	 * For example, to subtract 5 days from the current time of the date, you can achieve it by calling:<br>
	 * addTime(new Date(), Calendar.DAY_OF_MONTH, 5).
	 * 
	 * @param date
	 * @param field - the calendar field. e.g.: <b>Calendar.MINUTE</b>.
	 * @param amount - the amount of date or time to be added to the field.
	 * @return
	 */
	public static Date addTime(Date date, int field, int amount) {
		Calendar calendar = getCalendar(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * Compare two dates based on the fields.
	 * 
	 * @param date1
	 * @param date2
	 * @param fieldsToCompare - the calendar fields. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @return
	 */
	public static int compare(Date date1, Date date2, int[] fieldsToCompare) {
		Calendar calendar1 = getCalendar(date1);
		Calendar calendar2 = getCalendar(date2);

		Integer value1;
		Integer value2;
		int compare = 0;

		for(int field: fieldsToCompare) {
			value1 = calendar1.get(field);
			value2 = calendar2.get(field);

			compare = value1.compareTo(value2);

			if(compare == 0) {
				continue;
			}
			break;
		}
		return compare;
	}

	/**
	 * Calculate the difference between two dates.
	 * 
	 * @param date1
	 * @param date2
	 * @param diffIn - time unit to calculate the diff. e.g.: <b>CalendarUtil.IN_MINUTES</b>.
	 * @return
	 */
	public static long difference(Date date1, Date date2, int diffIn) {
		long differenceMilliSeconds = date2.getTime() - date1.getTime();
		return differenceMilliSeconds / diffIn;
	}

	/**
	 * Calculate the difference between two times of dates.
	 * 
	 * @param date1
	 * @param date2
	 * @param diffIn - time unit to calculate the diff. e.g.: <b>CalendarUtil.IN_MINUTES</b>.
	 * @return
	 */
	public static long differenceTime(Date date1, Date date2, int diffIn) {
		Calendar calendar1 = createCalendar(date1);
		Calendar calendar2 = createCalendar(calendar1, date2);

		return difference(calendar1.getTime(), calendar2.getTime(), diffIn);
	}

	/**
	 * Set calendar fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param calendar
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateGetFieldsFrom - date where #fieldsToSet will be getted from.
	 */
	public static void setFields(Calendar calendar, int[] fieldsToSet, Calendar dateGetFieldsFrom) {
		for(Integer field: fieldsToSet) {
			calendar.set(field, dateGetFieldsFrom.get(field));
		}
	}

	/**
	 * Set calendar fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param date
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateGetFieldsFrom - date where #fieldsToSet will be getted from.
	 */
	public static void setFields(Date date, int[] fieldsToSet, Calendar dateGetFieldsFrom) {
		Calendar calendar = getCalendar(date);
		setFields(calendar, fieldsToSet, dateGetFieldsFrom);
		date.setTime(calendar.getTimeInMillis());
	}

	/**
	 * Create a new calendar based on #baseDate and set its fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param baseDate - base date
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateSetFrom - date where #fieldsToSet will be getted from.
	 * @return
	 */
	public static Calendar createCalendar(Calendar baseDate, int[] fieldsToSet, Calendar dateGetFieldsFrom) {
		Calendar calendar = getCalendar(baseDate.getTime());
		setFields(calendar, fieldsToSet, dateGetFieldsFrom);
		return calendar;
	}

	/**
	 * Create a new calendar based on #baseDate and set its fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param baseDate - base date
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateGetFieldsFrom - date where #fieldsToSet will be getted from.
	 * @return
	 */
	public static Calendar createCalendar(Calendar baseDate, int[] fieldsToSet, Date dateGetFieldsFrom) {
		return createCalendar(baseDate, fieldsToSet, getCalendar(dateGetFieldsFrom));
	}

	/**
	 * Create a new calendar based on #baseDate and set its fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param baseDate - base date
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateSetFrom - date where #fieldsToSet will be getted from.
	 * @return
	 */
	public static Calendar createCalendar(Date baseDate, int[] fieldsToSet, Calendar dateGetFieldsFrom) {
		return createCalendar(getCalendar(baseDate), fieldsToSet, dateGetFieldsFrom);
	}

	/**
	 * Create a new calendar based on #baseDate and set its fields (#fieldsToSet) getting from #dateGetFieldsFrom.
	 * 
	 * @param baseDate - base date
	 * @param fieldsToSet - the calendar fields to be setted. e.g.: <b>CalendarUtil.HOUR_AND_MINUTE</b>.
	 * @param dateGetFieldsFrom - date where #fieldsToSet will be getted from.
	 * @return
	 */
	public static Calendar createCalendar(Date baseDate, int[] fieldsToSet, Date dateGetFieldsFrom) {
		return createCalendar(getCalendar(baseDate), fieldsToSet, getCalendar(dateGetFieldsFrom));
	}

	/**
	 * Create a new calendar based on #baseDate and set the time fields (HOUR_OF_DAY, MINUTE, SECOND and MILLISECOND)
	 * based on #baseTime.
	 * 
	 * @param baseDate - base date
	 * @param baseTime - base time
	 * @return a new calendar
	 */
	public static Calendar createCalendar(Calendar baseDate, Date baseTime) {
		Calendar calendarTemp = getCalendar(baseTime);
		Calendar calendar = (Calendar) baseDate.clone();

		calendar.set(Calendar.HOUR_OF_DAY, calendarTemp.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendarTemp.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendarTemp.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendarTemp.get(Calendar.MILLISECOND));

		return calendar;
	}

	/**
	 * Create a new calendar based on #baseDate and set the time fields (HOUR_OF_DAY, MINUTE, SECOND and MILLISECOND)
	 * based on #baseTime.
	 * 
	 * @param baseDate - base date
	 * @param baseTime - base time
	 * @return a new calendar
	 */
	public static Calendar createCalendar(Date baseDate, Date baseTime) {
		return createCalendar(getCalendar(baseDate), baseTime);
	}

	/**
	 * Create a new calendar and set its time fields (HOUR_OF_DAY, MINUTE, SECOND and MILLISECOND).
	 * 
	 * @param baseTime - base time
	 * @return a new calendar
	 */
	public static Calendar createCalendar(Date baseTime) {
		return createCalendar(Calendar.getInstance(), baseTime);
	}

	/**
	 * Create a calendar object from given date.
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try {
			Date date1 = sdf.parse("04/09/2011");
			Date date2 = sdf.parse("05/09/2011");
			System.out.println("--");
			System.out.println("Diferenca em milissegundos: " + difference(date1, date2, IN_MILLISECONDS));
			System.out.println("Diferenca em segundos: " + difference(date1, date2, IN_SECONDS));
			System.out.println("Diferenca em minutos: " + difference(date1, date2, IN_MINUTES));
			System.out.println("Diferenca em horas: " + difference(date1, date2, IN_HOURS));
			System.out.println("Diferenca em dias: " + difference(date1, date2, IN_DAYS));
		}
		catch(ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(new Date());

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(calendar1.getTime());
		calendar2.add(Calendar.MINUTE, 40);
		calendar2.add(Calendar.DAY_OF_MONTH, 3);

		System.out.println("--");
		System.out.println("Diferenca em segundos: " + difference(calendar1.getTime(), calendar2.getTime(), IN_SECONDS));
		System.out.println("Diferenca em minutos: " + difference(calendar1.getTime(), calendar2.getTime(), IN_MINUTES));
		System.out.println("Diferenca em horas: " + difference(calendar1.getTime(), calendar2.getTime(), IN_HOURS));
		System.out.println("Diff time em minutos: " + differenceTime(calendar1.getTime(), calendar2.getTime(), IN_MINUTES));

		System.out.println("--");
		System.out.println("Data atual mais 5 dias: " + addTime(new Date(), Calendar.DAY_OF_MONTH, 5));
		System.out.println("Data atual mais 1 mes: " + addTime(new Date(), Calendar.MONTH, 1));

		Date data1 = new Date();
		Date data2 = (Date) data1.clone();
		Date data3 = addTime(data1, Calendar.HOUR_OF_DAY, 1);
		Date data4 = addTime(data1, Calendar.HOUR_OF_DAY, 1);
		data4 = addTime(data4, Calendar.MINUTE, -10);

		System.out.println("--");
		System.out.println("Comparacao 1: " + compare(data1, data2, new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE}));
		System.out.println("Comparacao 2: " + compare(data1, data3, new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE}));
		System.out.println("Comparacao 3: " + compare(data3, data4, new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE}));
	}
}