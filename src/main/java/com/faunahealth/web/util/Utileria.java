package com.faunahealth.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utileria {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static int getYears(Date birthday, Date currentDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		int year = calendar.get(Calendar.YEAR);
		
		calendar.setTime(currentDate);
		int currentYear = calendar.get(Calendar.YEAR);
		
		return currentYear - year;
	}

	public static Date getTomorrowDate() throws ParseException {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		
		return dateFormat.parse(dateFormat.format(calendar.getTime()));
	}
	
}