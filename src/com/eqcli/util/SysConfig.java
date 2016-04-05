package com.eqcli.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.Property;

/**
 * 配置类,用于读取配置文件,配置数据库连接,配置网络ip及端口
 *
 */
public class SysConfig {

	private static Logger log = Logger.getLogger(SysConfig.class);
	
	//jdbc 配置
	private static String jdbcUser;
	private static String jdbcPasswd;
	private static String jdbcDb;
	private static int jdbcPort;
	private static String jdbcServerName;

	//网络参数配置
	private static String serverIp;
	private static int serverPort;

	/** 预配置,读取文件,设置相应参数值 */
	public static void preConfig(){
		Properties  prop = getPropertiesFromFile();
		if(prop != null){
			//数据库配置
			jdbcUser = prop.getProperty("JDBC_USER");
			jdbcPasswd = prop.getProperty("JDBC_PASSWORD");
			jdbcDb = prop.getProperty("JDBC_DATABASE");
			jdbcPort = Integer.valueOf(prop.getProperty("JDBC_PORT"));
			jdbcServerName = prop.getProperty("JDBC_SERVERNAME");
			//服务器配置
			serverIp = prop.getProperty("SERVER_IP", "localhost");
			serverPort = Integer.valueOf(prop.getProperty("SERVER_PORT", "3306"));
			//系统信息配置
			
		}else{
			log.error("配置文件读取失败!");
		}
		
	}
	
	/** 通过配置文件读取配置 */
	private static Properties getPropertiesFromFile(){
		
		Properties prop = new Properties();
		
		//根据工程路径找到配置文件(目前配置文件放在工程根路径下)
		String workDir = System.getProperty("user.dir");
		try{
			InputStream inputStream = new FileInputStream(workDir+"/config.properties");
			prop.load(inputStream);
		}catch(IOException e){
			e.printStackTrace();
			return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		}
		return prop;
	}
	
	public static String getJdbcUser() {
		return jdbcUser;
	}

	public static String getJdbcPasswd() {
		return jdbcPasswd;
	}

	public static String getJdbcDb() {
		return jdbcDb;
	}

	public static int getJdbcPort() {
		return jdbcPort;
	}

	public static String getJdbcServerName() {
		return jdbcServerName;
	}

	public static String getServerIp() {
		return serverIp;
	}

	public static int getServerPort() {
		return serverPort;
	}
}
