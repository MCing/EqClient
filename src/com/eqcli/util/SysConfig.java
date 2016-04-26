package com.eqcli.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

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
	
	//台网配置
	private static String authenCode;
	private static String serverId;

	//网络参数配置
	private static String serverIp;
	private static int serverPort;
	
	public static final String CONFIG_PATH = System.getProperty("user.dir") + "/config.properties";

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
			
			//网络参数配置
			authenCode = prop.getProperty("AUTHEN_CODE", "");
			serverId = prop.getProperty("SERVER_ID", "TT");
			
			//台站配置信息
			Random random = new Random();
			EqConfig.stdId =  prop.getProperty("STATION_ID",String.format("%05d", new Random().nextInt(99999)));				//烈度仪代号
			EqConfig.longitude =  Float.valueOf( prop.getProperty("LONGITUDE",String.valueOf(((float)random.nextInt(300)*100000)/100000)));				//经度 度＊100000
			EqConfig.latitude =  Float.valueOf(prop.getProperty("LATITUDE",String.valueOf(((float)random.nextInt(300)*100000)/100000)));				//纬度 度＊100000
			EqConfig.altitude =  Short.valueOf(prop.getProperty("ALTITUDE",String.valueOf(random.nextInt(200))));				//高程 单位M
			EqConfig.sensitivity =  Integer.valueOf(prop.getProperty("SENSITIVITY",String.valueOf(random.nextInt(1000))));			//灵敏度
			EqConfig.transMode =  Short.valueOf(prop.getProperty("TRANSMODE",String.valueOf(Constant.MODE_CONTINUOUS)));			//传输模式  1:连续传输模式  2:为触发传输传波形  3:触发传输不传波形
			EqConfig.triggerThreshold =  Short.valueOf(prop.getProperty("THRESHOLD",String.valueOf(random.nextInt(200))));		//触发阀值
			
			
		}else{
			log.error("配置文件读取失败!");
		}
		
	}
	
	public static String getAuthenCode() {
		return authenCode;
	}

	public static void setAuthenCode(String authenCode) {
		SysConfig.authenCode = authenCode;
	}

	public static String getServerId() {
		return serverId;
	}

	public static void setServerId(String serverId) {
		SysConfig.serverId = serverId;
	}

	/** 通过配置文件读取配置 */
	private static Properties getPropertiesFromFile(){
		
		Properties prop = new Properties();
		
		//根据工程路径找到配置文件(目前配置文件放在工程根路径下)
		try{
			InputStream inputStream = new FileInputStream(CONFIG_PATH);
			prop.load(inputStream);
		}catch(IOException e){
			e.printStackTrace();
			return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		}
		return prop;
	}
	
	/** 保存Properties对象到配置文件 */
	private static void saveProperty(Properties prop){
		try {
			OutputStream out = new FileOutputStream(CONFIG_PATH);
			prop.store(out, new Date().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** 保存新的数据库配置到配置文件 */
	public static void saveDbConfig(String serverName, String userName, String password){
		Properties pro = getPropertiesFromFile();
		
		jdbcUser = userName;
		jdbcPasswd = password;
		jdbcServerName = serverName;
		
		pro.put("JDBC_SERVERNAME", serverName);
		pro.put("JDBC_USER", userName);
		pro.put("JDBC_PASSWORD", password);
		
		saveProperty(pro);
	}
	
	/** 保存新的台网服务器配置到文件  */
	public static void saveServerConfig(String srvIp, String port, String srvId, String code){
		authenCode = code;
		serverId = srvId;
		serverIp = srvIp;
		serverPort = Integer.valueOf(port);
		Properties pro = getPropertiesFromFile();
		pro.put("SERVER_IP", serverIp);
		pro.put("SERVER_PORT", port);
		pro.put("SERVER_ID", serverId);
		pro.put("AUTHEN_CODE", authenCode);
		saveProperty(pro);
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
