package com.eqcli.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class JDBCHelper {

	private static Logger log = Logger.getLogger(JDBCHelper.class);

	// 数据库连接池连接容量
	private static final int connectionPoolCapacity = 50;

	private static MiniConnectionPoolManager poolMgr;

	public static void initDB() {

		// 使用连接池,数据源
		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();

		ds.setServerName(SysConfig.getJdbcServerName());
		ds.setPort(SysConfig.getJdbcPort());
		ds.setDatabaseName(SysConfig.getJdbcDb());
		ds.setUser(SysConfig.getJdbcUser());
		ds.setPassword(SysConfig.getJdbcPasswd());

		poolMgr = new MiniConnectionPoolManager(ds, connectionPoolCapacity);

		testPrepareDb();

		try {
			Connection testConn = poolMgr.getConnection();
			testConn.close();
		} catch (Exception e) {
			log.error("数据库配置错误:" + e.getMessage());
		}
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
			log.error("数据库异常:" + e.getMessage());
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
			log.error("数据库异常:" + e.getMessage());
		}
	}

	/**
	 * 关闭连接池
	 */
	public static void closeDB() {
		try {
			if (poolMgr != null) {
				poolMgr.dispose();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("数据库连接池关闭异常:"+e.getMessage());
		}
	}

	// 为方便客户端测试
	// 创建临时数据库
	private static void testPrepareDb() {

		String tmpDatabase = "test" + EqConfig.stdId;
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
			ds.setServerName(SysConfig.getJdbcServerName());
			ds.setPort(SysConfig.getJdbcPort());
			ds.setDatabaseName(tmpDatabase);
			ds.setUser(SysConfig.getJdbcUser());
			ds.setPassword(SysConfig.getJdbcPasswd());
			poolMgr = new MiniConnectionPoolManager(ds, connectionPoolCapacity);

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

	private static String createTableSql = "CREATE TABLE `wavefdata_t` ("
			+ "  `id` int(11) NOT NULL,"
			+ "  `qid` char(1) NOT NULL DEFAULT 'D',"
			+ "  `hreserve` tinyint(1) unsigned DEFAULT NULL,"
			+ "`localid` char(2) NOT NULL," + "`channid` char(2) NOT NULL,"
			+ "`starttime` bigint(20) NOT NULL,"
			+ "`samcount` smallint(6) NOT NULL,"
			+ "`samfactor` smallint(6) NOT NULL,"
			+ "`sammul` smallint(6) NOT NULL,"
			+ "`actid` tinyint(4) unsigned NOT NULL,"
			+ "`iocflag` tinyint(4) unsigned NOT NULL,"
			+ "`dataqflag` tinyint(4) unsigned NOT NULL,"
			+ "`blockcount` tinyint(4) unsigned NOT NULL,"
			+ "`timecorr` int(11) NOT NULL,"
			+ "`dataoffs` smallint(6) NOT NULL,"
			+ " `subblockoffs` smallint(6) NOT NULL,"
			+ " `subheadtype` smallint(6) NOT NULL,"
			+ " `nextblockid` smallint(6) NOT NULL,"
			+ "  `codeformat` tinyint(4) NOT NULL,"
			+ " `byteorder` tinyint(4) unsigned NOT NULL,"
			+ " `datalen` tinyint(4) unsigned NOT NULL,"
			+ " `subhreserve` tinyint(4) unsigned DEFAULT NULL,"
			+ " `subblocktype` smallint(6) NOT NULL,"
			+ " `dimension` tinyint(1) unsigned NOT NULL,"
			+ " `subbreserve` char(1) DEFAULT NULL,"
			+ "  `sensfactor` int(11) NOT NULL,"
			+ " `datablock` tinyblob NOT NULL," + "  PRIMARY KEY (`id`)" + ");";
}
