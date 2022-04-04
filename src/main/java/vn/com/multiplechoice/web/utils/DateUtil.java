package vn.com.multiplechoice.web.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Date getFirstDateOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}
}
