package com.faunahealth.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utileria {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final int NEXT_DAYS = 4; 

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
	
	public static List<String> getNextDays() {
		
		Date startDate = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, NEXT_DAYS);
		Date endDate = calendar.getTime();
		
		GregorianCalendar gcalendar = new GregorianCalendar();
		gcalendar.setTime(startDate);
		List<String> nextDays = new ArrayList<>();
		
		while(!gcalendar.getTime().after(endDate)) {
			Date date = gcalendar.getTime();
			gcalendar.add(Calendar.DATE, 1);
			nextDays.add(dateFormat.format(date));
		}
		
		return nextDays;
		
	}
	
}