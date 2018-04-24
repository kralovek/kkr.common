package kkr.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilsDate {

	public static boolean isTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return false //
				|| calendar.get(Calendar.MILLISECOND) != 0 //
				|| calendar.get(Calendar.SECOND) != 0 //
				|| calendar.get(Calendar.MINUTE) != 0 //
				|| calendar.get(Calendar.HOUR_OF_DAY) != 0;
	}

}
