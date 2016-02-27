package com.eqcli.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class JDBCHelper {

	private static final String USER = "root";
	private static final String PASSWORD = "ldyy";
	private static final String DATABASE = "clientdb";
	private static final int PORT = 3306;
	private static final String SERVERNAME = "127.0.0.1";
	//private static final String DRIVER = "com.mysql.jdbc.Driver";
	//private static final String URL = "jdbc:mysql://localhost";
	
	private static MiniConnectionPoolManager  poolMgr;
	
	/**
	 * deprerate 加载jdbc驱动,必须在使用jdbc前加载
	 */
	public static void initDB(){
//		try{
//			Class.forName(DRIVER);
//		}catch(ClassNotFoundException e){
//			System.out.println("驱动加载失败");
//		}
		//使用连接池,数据源
		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
		
		ds.setServerName(SERVERNAME);
		ds.setPort(PORT);
		ds.setDatabaseName(DATABASE);
		ds.setUser(USER);
		ds.setPassword(PASSWORD);
		
		poolMgr = new MiniConnectionPoolManager(ds, 20);
		
	}
	
	/**
	 * 获取一个Connection 连接
	 * @return
	 */
	public static Connection getDBConnection(){
		
		Connection conn = null;
		try {
			conn = poolMgr.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * 关闭jdbc Connection 连接
	 * @param conn
	 */
	public static void closeDBConnection(Connection conn){
		
		try{
			if(conn != null){
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭连接池
	 */
	public static void closeDB(){
		try {
			poolMgr.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
