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
		
		String timeStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		timeStr = sdf.format(new Date(time));
		
		return timeStr;
	}
}
