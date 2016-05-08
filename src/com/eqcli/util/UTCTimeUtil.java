package com.eqcli.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * UTC 时间转换工具类
 *
 */
public class UTCTimeUtil {

	/** 获取当前UTC 时间
	 * 
	 * @return  当前UTC 时间 ,毫秒
	 */
	public static long getCurrUTCTime(){
		
		return getUTCTimeLong(new Date().getTime());
	}
	
	/**
	 * 根据所给时间,获取UTC时间
	 * @param time
	 * @return
	 */
	public static long getUTCTimeLong(long time){
		Calendar cal = Calendar.getInstance();
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		
		//计算UTC时间
		cal.setTimeInMillis(time);
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}
	
	
	/** 根据时间毫秒值转化成指定时间格式 */
	public static String timeFormat1(long time){
		
		String timeStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		timeStr = sdf.format(new Date(time));
		
		return timeStr;
	}
	
	public static String timeFormat2(long time){
		
		String timeStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		timeStr = sdf.format(new Date(time));
		
		return timeStr;
	}
}
