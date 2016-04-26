package com.bnade.util;

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
}
