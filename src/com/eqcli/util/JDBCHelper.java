package com.eqcli.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class JDBCHelper {

	private static final String USER = "root";
	private static final String PASSWORD = "ldyy";
	private static final String DATABASE = "clientdb";
	// private static final String DATABASE = "clientdb";
	private static final int PORT = 3306;
	private static final String SERVERNAME = "127.0.0.1";

	// private static final String DRIVER = "com.mysql.jdbc.Driver";
	// private static final String URL = "jdbc:mysql://localhost";

	private static MiniConnectionPoolManager poolMgr;

	/**
	 * deprerate 加载jdbc驱动,必须在使用jdbc前加载
	 */
	public static void initDB() {
		// try{
		// Class.forName(DRIVER);
		// }catch(ClassNotFoundException e){
		// System.out.println("驱动加载失败");
		// }
		// 使用连接池,数据源
		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();

		ds.setServerName(SERVERNAME);
		ds.setPort(PORT);
		ds.setDatabaseName(DATABASE);
		ds.setUser(USER);
		ds.setPassword(PASSWORD);

		poolMgr = new MiniConnectionPoolManager(ds, 20);

		testPrepareDb();
		// test 执行sql脚本创建数据库表
		// ScriptRunner runner;
		// try {
		// runner = new ScriptRunner(poolMgr.getConnection());
		// runner.setAutoCommit(true);
		// runner.runScript(Resources.getResourceAsReader("wavefdata_t.sql"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 获取一个Connection 连接
	 * 
	 * @return
	 */
	public static Connection getDBConnection() {

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
	 * 
	 * @param conn
	 */
	public static void closeDBConnection(Connection conn) {

		try {
			if (conn != null) {
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
	public static void closeDB() {
		try {
			poolMgr.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 为方便客户端测试
	// 创建临时数据库
	private static void testPrepareDb() {

		String tmpDatabase = "test" + Constant.stationId;
		Connection conn = getDBConnection();
		try {
			// 创建数据库
			Statement stat = conn.createStatement();
			stat.executeUpdate("create database " + tmpDatabase);

			stat.close();
			conn.close();
			
			// 重新配置数据库配置
			closeDB();
			MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
			ds.setServerName(SERVERNAME);
			ds.setPort(PORT);
			ds.setDatabaseName(tmpDatabase);
			ds.setUser(USER);
			ds.setPassword(PASSWORD);
			poolMgr = new MiniConnectionPoolManager(ds, 20);
			
			// 创建表格
			conn = getDBConnection();
			stat = conn.createStatement();
			stat.executeUpdate(createTableSql);

			stat.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String createTableSql = "CREATE TABLE `wavefdata_t` (" + "  `id` int(11) NOT NULL,"
			+ "  `qid` char(1) NOT NULL DEFAULT 'D'," + "  `hreserve` tinyint(1) unsigned DEFAULT NULL,"
			+ "`localid` char(2) NOT NULL," + "`channid` char(2) NOT NULL," + "`starttime` bigint(20) NOT NULL,"
			+ "`samcount` smallint(6) NOT NULL," + "`samfactor` smallint(6) NOT NULL,"
			+ "`sammul` smallint(6) NOT NULL," + "`actid` tinyint(4) unsigned NOT NULL,"
			+ "`iocflag` tinyint(4) unsigned NOT NULL," + "`dataqflag` tinyint(4) unsigned NOT NULL,"
			+ "`blockcount` tinyint(4) unsigned NOT NULL," + "`timecorr` int(11) NOT NULL,"
			+ "`dataoffs` smallint(6) NOT NULL," + " `subblockoffs` smallint(6) NOT NULL,"
			+ " `subheadtype` smallint(6) NOT NULL," + " `nextblockid` smallint(6) NOT NULL,"
			+ "  `codeformat` tinyint(4) NOT NULL," + " `byteorder` tinyint(4) unsigned NOT NULL,"
			+ " `datalen` tinyint(4) unsigned NOT NULL," + " `subhreserve` tinyint(4) unsigned DEFAULT NULL,"
			+ " `subblocktype` smallint(6) NOT NULL," + " `dimension` tinyint(1) unsigned NOT NULL,"
			+ " `subbreserve` char(1) DEFAULT NULL," + "  `sensfactor` int(11) NOT NULL,"
			+ " `datablock` tinyblob NOT NULL," + "  PRIMARY KEY (`id`)" + ");";
}
