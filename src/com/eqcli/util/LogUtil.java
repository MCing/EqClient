package com.eqcli.util;

import java.net.URL;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.log4j.PropertyConfigurator;

import com.eqcli.application.LogEvent;

/** 
 * 日志工具类
 * 使用log4j包
 *
 */
public class LogUtil {

	private final static String LOG_PROPERTY_FILE = "log4j.properties";
	
	//系统日志
	private static ObservableList<LogEvent> logList = FXCollections.observableArrayList();
	
	public static void initLog(){
		
//		使用代码配置,为了调试多个客户端,这里用代码配置,以配置不同的日志输出路径
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "INFO, ServerDailyRollingFile,CONSOLE");
		prop.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{HH:mm:ss,SSS} [%t] %-5p %C{1} : %m%n");
		
		prop.setProperty("log4j.appender.ServerDailyRollingFile","org.apache.log4j.DailyRollingFileAppender");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.DatePattern", "'.'yyyy-MM-dd ");
		
		//配置不同的输出路径
		prop.setProperty("log4j.appender.ServerDailyRollingFile.File", "D://logs/clientlog"+EqConfig.stdId+".log ");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout.ConversionPattern", "%d{MM-dd HH:mm:ss} %p [%c] %m%n");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.Append", "true");
		PropertyConfigurator.configure(prop);
		

		//通过文件配置log4j
//		try {
//			URL url = loadPropertiesURL();
//			if(url == null){
//				throw new Exception("读不到配置文件");
//			}
//			PropertyConfigurator.configure(url);
//		}  catch(Exception e){
//			System.err.println("log 配置错误:"+e.getMessage());
//		}
	}
	
	/** 提供接口,供多种方法获取log4j配置文件 */
	private static URL loadPropertiesURL(){
		
		//1. 通过系统路径获取配置文件
//		String workDir = System.getProperty("user.dir");
//		try {
//			URL propURL = new File(workDir+"/"+LOG_PROPERTY_FILE).toURI().toURL();
//			return propURL;
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		
		//2. 通过classpath 获取配置文件,文件必须在classpath路径中(如bin目录下)
		return ClassLoader.getSystemResource(LOG_PROPERTY_FILE);
	}
	
	public static ObservableList<LogEvent> getSysLog(){
		return logList;
	}
	/** 添加是系统事件  */
	public static void sysLog(String msg){
		LogEvent event = new LogEvent();
		event.setTime(UTCTimeUtil.timeFormat1(System.currentTimeMillis()));
		event.setEvent(msg);
		logList.add(event);
	}
	
}
