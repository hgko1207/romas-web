package net.woori.romas.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 날짜 관련 유틸
 * 
 * @author hgko
 *
 */
public class DateUtil {

	/**
	 * 날짜 비교
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long calDiffDays(Date startDate, Date endDate) {
		
		long calDate = endDate.getTime() - startDate.getTime(); 

		return calDate / (24 * 60 * 60 * 1000);
	}
	
	/**
	 * 날짜 가져오기
	 * @param value
	 * @return
	 */
	public static String getDate(int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, value);
		
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
}
