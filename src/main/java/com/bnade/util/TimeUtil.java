package com.bnade.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;

	public static String format(long millisecond) {
		StringBuffer sb = new StringBuffer();
		long day = millisecond / DAY;
		if (day > 0) {
			sb.append(day);
			sb.append("天");
			millisecond = millisecond - day * DAY;
		}
		long hour = millisecond / HOUR;
		if (hour > 0) {
			sb.append(hour);
			sb.append("小时");
			millisecond = millisecond - hour * HOUR;
		}
		long minute = millisecond / MINUTE;
		if (minute > 0) {
			sb.append(minute);
			sb.append("分");
			millisecond = millisecond - minute * MINUTE;
		}
		long second = millisecond / SECOND;
		if (second > 0) {
			sb.append(second);
			sb.append("秒");
			millisecond = millisecond - second * SECOND;
		}
		if (millisecond > 0) {
			sb.append(millisecond);
			sb.append("毫秒");
		}
		return sb.toString();
	}
	
	/**
	 * 返回当天日期
	 * @return
	 */
	public static String getDay() {
		SimpleDateFormat sf = new SimpleDateFormat("MMdd");
		return sf.format(new Date());
	}
	
	/**
	 * 返回比当天差i天的日期
	 * @return
	 */
	public static String getDay(int i) {
		SimpleDateFormat sf = new SimpleDateFormat("MMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, i);
		return sf.format(cal.getTime());
	}
	
	public static void main(String[] args) {
		System.out.println(TimeUtil.getDay());
		System.out.println(TimeUtil.getDay(-1));
	}
}
