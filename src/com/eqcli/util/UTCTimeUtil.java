package com.eqcli.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UTCTimeUtil {

	
	public static Long getUTCTimeLong(){
		
		Calendar cal = Calendar.getInstance();
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		
		//计算UTC时间
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}
	
	public static String parseUTCTime2Str(long time){
		
		return getTimeStr(time, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	public static String getSimpleTimeStr(long time){
		
		return getTimeStr(time, "yy/MM/dd HH:mm:ss");
	}
	
	private static String getTimeStr(long time, String format){
		
		String timeStr;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		timeStr = sdf.format(new Date(time));
		
		return timeStr;
	}
}
