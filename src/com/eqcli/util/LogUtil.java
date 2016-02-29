package com.eqcli.util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/** 
 * 日志工具类
 * 使用log4j包
 *
 */
public class LogUtil {

	
	public static void initLog(){
		
//		使用代码配置,为了调试多个客户端,这里用代码配置,以配置不同的日志输出路径
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "DEBUG, ServerDailyRollingFile,CONSOLE");
		prop.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{HH:mm:ss,SSS} [%t] %-5p %C{1} : %m%n");
		
		prop.setProperty("log4j.appender.ServerDailyRollingFile","org.apache.log4j.DailyRollingFileAppender");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.DatePattern", "'.'yyyy-MM-dd ");
		
		//配置不同的输出路径
		prop.setProperty("log4j.appender.ServerDailyRollingFile.File", "D://logs/clientlog"+Constant.stationId+".log ");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout.ConversionPattern", "%d{MM-dd HH:mm:ss} %p [%c] %m%n");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.Append", "true");
		PropertyConfigurator.configure(prop);
		
		//使用文件配置,需要把配置文件放在classpath下(可在bin目录下)
//		PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
	}
	
}
