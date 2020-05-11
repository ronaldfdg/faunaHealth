package com.faunahealth.web.util;

import java.util.Calendar;
import java.util.Date;

public class Utileria {
	
	public static int getYears(Date birthday, Date currentDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		int year = calendar.get(Calendar.YEAR);
		
		calendar.setTime(currentDate);
		int currentYear = calendar.get(Calendar.YEAR);
		
		return currentYear - year;
	}
	
}
