package com.eqcli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.PooledConnection;

import com.eqcli.util.JDBCHelper;
import com.eqsys.msg.data.TrgData;

public class TrgDataDao{

	private static final String TableName = "triggerdata_t";
	private static String insertSql = "insert into "+TableName+
			"(starttimesec, starttimemsec, relattime, stalta, initmotiondir, "
			+ "ud2pga, ud2pgv, ud2pgd, ew2pga, ew2pgv,ew2pgd, ns2pga, ns2pgv, ns2pgd,"
			+ "ud2psa03, ud2psa10, ud2psa30, ew2psa03, ew2psa10,ew2psa30, ns2psa03, ns2psa10, ns2psa30,"
			+ "intensity) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static String deleteSql = "delete from "+ TableName+ " where packetid=?;";
	
	public static boolean save(TrgData t) {
		
		boolean ret = false;
		Connection conn = null;
		PreparedStatement preStat = null;
		try {
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(insertSql);
			preStat.setLong(1, 10);
			preStat.setShort(2, t.getStartTimeMs());
			preStat.setShort(3, t.getRelTimeSec());
			preStat.setInt(4, t.getStaAndltaValue());
			preStat.setShort(5, t.getInitMotionDir());
			preStat.setInt(6, t.getUdToPga());
			preStat.setInt(7, t.getUdToPgv());
			preStat.setInt(8, t.getUdToPgd());
			preStat.setInt(9, t.getEwToPga());
			preStat.setInt(10, t.getEwToPgv());
			preStat.setInt(11, t.getEwToPgd());
			preStat.setInt(12, t.getNsToPga());
			preStat.setInt(13, t.getNsToPgv());
			preStat.setInt(14, t.getNsToPgd());
			preStat.setInt(15, t.getUdToPsa03());
			preStat.setInt(16, t.getUdToPsa10());
			preStat.setInt(17, t.getUdToPsa30());
			preStat.setInt(18, t.getEwToPsa03());
			preStat.setInt(19, t.getEwToPsa10());
			preStat.setInt(20, t.getEwToPsa30());
			preStat.setInt(21, t.getNsToPsa03());
			preStat.setInt(22, t.getNsToPsa10());
			preStat.setInt(23, t.getNsToPsa30());
			preStat.setInt(24, t.getIntensity());
			int insertRet = preStat.executeUpdate();
			System.out.println("insert:"+insertRet);
			ret = true;
		} catch (SQLException e) {

			ret = false;
			e.printStackTrace();
		}finally{
			try {
				if(preStat != null){
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return ret;
	}

	public static boolean delete(TrgData t) {
		
		Connection conn = null;
		PreparedStatement preStat = null;
		try {
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(deleteSql);
			preStat.setInt(1, t.getId());
			preStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(preStat != null)
				preStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCHelper.closeDBConnection(conn);
		}
		return false;
	}
}
