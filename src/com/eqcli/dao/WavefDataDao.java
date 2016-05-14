package com.eqcli.dao;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.eqcli.util.JDBCHelper;
import com.eqsys.msg.data.WavefData;

public class WavefDataDao{

	private static String mTableName = "wavefdata_t";
	private static String mInsertSql = "insert into " + mTableName
			+ "(id, qid, localid, channid, starttime, samcount, samfactor, sammul, actid, iocflag, dataqflag, blockcount, timecorr, dataoffs, subblockoffs, subheadtype, nextblockid, codeformat, byteorder, datalen, subblocktype, dimension, sensfactor, datablock) "
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static boolean save(WavefData t) {

		boolean ret = false;
		PreparedStatement preStat = null;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(mInsertSql);
			preStat.setInt(1, t.getId());
			preStat.setString(2, ""+t.getQuality());
			preStat.setString(3, t.getLocId());
			preStat.setString(4, t.getChannId());
			preStat.setLong(5, t.getStartTime());
			preStat.setShort(6, t.getSamNum());
			preStat.setShort(7, t.getSamFactor());
			preStat.setShort(8, t.getSamMul());
			preStat.setByte(9, t.getActFlag());
			preStat.setByte(10,t.getIocFlag());
			preStat.setByte(11,t.getDqFlag());
			preStat.setByte(12,t.getBlockNum());
			preStat.setInt(13, t.getTimeCorr());
			preStat.setShort(14, t.getStartOffs());
			preStat.setShort(15, t.getSubBlockOffs());
			preStat.setShort(16, t.gethBlockType());
			preStat.setShort(17, t.getBlockId());
			preStat.setByte(18, t.getCodeFormat());
			preStat.setByte(19, t.getOrder());
			preStat.setByte(20, t.getDataLen());
			preStat.setShort(21, t.getBlockType());
			preStat.setByte(22, t.getDim());
			preStat.setInt(23, t.getSensFactor());
			preStat.setBlob(24, new ByteArrayInputStream(t.getDataBlock()));
			int insertRet = preStat.executeUpdate();
			ret = true;
		} catch (SQLException e) {

			ret = false;
			e.printStackTrace();
		} finally {
			try {
				if (preStat != null) {
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return ret;
	}

	/** 根据id从数据库中获取数据
	 * @param start  起始id  
	 * @param count  数量
	 */
	public static List<WavefData> get(int start, int count) {
		String sql = "select * from " + mTableName + " where id>=? limit ?;";
		List<WavefData> list = new ArrayList<WavefData>();
		PreparedStatement preStat = null;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(sql);
			preStat.setInt(1, start);
			preStat.setInt(2, count);
			ResultSet result = preStat.executeQuery();
			while(result.next()){
				WavefData data = new WavefData();
				data.setId(result.getInt("id"));
				data.setQuality(result.getString("qid"));
				data.setLocId(result.getString("localid"));   //2Bytes
				data.setChannId(result.getString("channid"));     //2Bytes
				data.setStartTime(result.getLong("starttime"));		 //
				data.setSamNum(result.getShort("samcount"));
				data.setSamFactor(result.getShort("samfactor"));
				data.setSamMul(result.getShort("sammul"));
				data.setActFlag(result.getByte("actid"));
				data.setIocFlag(result.getByte("iocflag"));
				data.setDqFlag(result.getByte("dataqflag"));
				data.setBlockNum(result.getByte("blockcount"));
				data.setTimeCorr(result.getInt("timecorr"));
				data.setStartOffs(result.getShort("dataoffs"));
				data.setSubBlockOffs(result.getShort("subblockoffs"));
				//sub block header
				data.setOrder(result.getByte("byteorder"));
				data.setCodeFormat(result.getByte("codeformat"));
				data.setDataLen(result.getByte("datalen"));
				data.setBlockId(result.getShort("nextblockid"));
				data.sethBlockType(result.getShort("subheadtype"));
				//sub block
				data.setDim(result.getByte("dimension"));
				data.setSensFactor(result.getInt("sensfactor"));
				data.setBlockType(result.getShort("subblocktype"));
				//data
				Blob block = result.getBlob("datablock");
				data.setDataBlock(block.getBytes(1, (int) block.length()));
				list.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (preStat != null) {
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return list;
	}
	
	/**
	 * 获取当前数据库中最大id值
	 * @return
	 */
	public static int getLastId(){
		String sql = "select max(id) from wavefdata_t;";
		Statement stat = null;
		int id = 0;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			stat = conn.createStatement();
			ResultSet result = stat.executeQuery(sql);
			if(result.next()){
				id = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return id;
	}

	//尝试通用sql条件查询，未完成
//	public List<WavefData> get(String wherefield, String condition, String value, int limitValue) {
//		String sql = "select * from " + mTableName + " where " + wherefield + " " + condition + " ? limit ?;"; 
//		return null;
//	}

	public static List<WavefData> get() {
		// TODO Auto-generated method stub
		return null;
	}
	//条件 select * from table where a>/</= b limit number
	
	public static List<WavefData> getLast30(long time){
		long startTime = time - 30000;
		long endTime = time;
		List<WavefData> list = new ArrayList<WavefData>();
		String sql = "select * from " + mTableName + " where starttime > ? and starttime < ?;";
		
		PreparedStatement preStat = null;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(sql);
			preStat.setLong(1, startTime);
			preStat.setLong(2, endTime);
			ResultSet result = preStat.executeQuery();
			while(result.next()){
				WavefData data = new WavefData();
				data.setId(result.getInt("id"));
				data.setQuality(result.getString("qid"));
				data.setLocId(result.getString("localid"));   //2Bytes
				data.setChannId(result.getString("channid"));     //2Bytes
				data.setStartTime(result.getLong("starttime"));		 //
				data.setSamNum(result.getShort("samcount"));
				data.setSamFactor(result.getShort("samfactor"));
				data.setSamMul(result.getShort("sammul"));
				data.setActFlag(result.getByte("actid"));
				data.setIocFlag(result.getByte("iocflag"));
				data.setDqFlag(result.getByte("dataqflag"));
				data.setBlockNum(result.getByte("blockcount"));
				data.setTimeCorr(result.getInt("timecorr"));
				data.setStartOffs(result.getShort("dataoffs"));
				data.setSubBlockOffs(result.getShort("subblockoffs"));
				//sub block header
				data.setOrder(result.getByte("byteorder"));
				data.setCodeFormat(result.getByte("codeformat"));
				data.setDataLen(result.getByte("datalen"));
				data.setBlockId(result.getShort("nextblockid"));
				data.sethBlockType(result.getShort("subheadtype"));
				//sub block
				data.setDim(result.getByte("dimension"));
				data.setSensFactor(result.getInt("sensfactor"));
				data.setBlockType(result.getShort("subblocktype"));
				//data
				Blob block = result.getBlob("datablock");
				data.setDataBlock(block.getBytes(1, (int) block.length()));
				list.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (preStat != null) {
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return list;
	}

	/** 获取触发时的波形数据  */
	public static List<WavefData> getTrgData(int id, long endtime) {
		List<WavefData> list = new ArrayList<WavefData>();
		String sql = "select * from " + mTableName + " where id > ? and starttime < ?;";
		
		PreparedStatement preStat = null;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(sql);
			preStat.setLong(1, id);
			preStat.setLong(2, endtime);
			ResultSet result = preStat.executeQuery();
			while(result.next()){
				WavefData data = new WavefData();
				data.setId(result.getInt("id"));
				data.setQuality(result.getString("qid"));
				data.setLocId(result.getString("localid"));   //2Bytes
				data.setChannId(result.getString("channid"));     //2Bytes
				data.setStartTime(result.getLong("starttime"));		 //
				data.setSamNum(result.getShort("samcount"));
				data.setSamFactor(result.getShort("samfactor"));
				data.setSamMul(result.getShort("sammul"));
				data.setActFlag(result.getByte("actid"));
				data.setIocFlag(result.getByte("iocflag"));
				data.setDqFlag(result.getByte("dataqflag"));
				data.setBlockNum(result.getByte("blockcount"));
				data.setTimeCorr(result.getInt("timecorr"));
				data.setStartOffs(result.getShort("dataoffs"));
				data.setSubBlockOffs(result.getShort("subblockoffs"));
				//sub block header
				data.setOrder(result.getByte("byteorder"));
				data.setCodeFormat(result.getByte("codeformat"));
				data.setDataLen(result.getByte("datalen"));
				data.setBlockId(result.getShort("nextblockid"));
				data.sethBlockType(result.getShort("subheadtype"));
				//sub block
				data.setDim(result.getByte("dimension"));
				data.setSensFactor(result.getInt("sensfactor"));
				data.setBlockType(result.getShort("subblocktype"));
				//data
				Blob block = result.getBlob("datablock");
				data.setDataBlock(block.getBytes(1, (int) block.length()));
				list.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (preStat != null) {
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return list;
	}

	/** 根据开始时间和结束时间获取波形数据 */
	public static List<WavefData> get(long starttime, long endtime) {

		List<WavefData> list = new ArrayList<WavefData>();
		String sql = "select * from " + mTableName + " where starttime > ? and starttime < ?;";
		
		PreparedStatement preStat = null;
		Connection conn = null;
		try{
			conn = JDBCHelper.getDBConnection();
			preStat = conn.prepareStatement(sql);
			preStat.setLong(1, starttime);
			preStat.setLong(2, endtime);
			ResultSet result = preStat.executeQuery();
			while(result.next()){
				WavefData data = new WavefData();
				data.setId(result.getInt("id"));
				data.setQuality(result.getString("qid"));
				data.setLocId(result.getString("localid"));   //2Bytes
				data.setChannId(result.getString("channid"));     //2Bytes
				data.setStartTime(result.getLong("starttime"));		 //
				data.setSamNum(result.getShort("samcount"));
				data.setSamFactor(result.getShort("samfactor"));
				data.setSamMul(result.getShort("sammul"));
				data.setActFlag(result.getByte("actid"));
				data.setIocFlag(result.getByte("iocflag"));
				data.setDqFlag(result.getByte("dataqflag"));
				data.setBlockNum(result.getByte("blockcount"));
				data.setTimeCorr(result.getInt("timecorr"));
				data.setStartOffs(result.getShort("dataoffs"));
				data.setSubBlockOffs(result.getShort("subblockoffs"));
				//sub block header
				data.setOrder(result.getByte("byteorder"));
				data.setCodeFormat(result.getByte("codeformat"));
				data.setDataLen(result.getByte("datalen"));
				data.setBlockId(result.getShort("nextblockid"));
				data.sethBlockType(result.getShort("subheadtype"));
				//sub block
				data.setDim(result.getByte("dimension"));
				data.setSensFactor(result.getInt("sensfactor"));
				data.setBlockType(result.getShort("subblocktype"));
				//data
				Blob block = result.getBlob("datablock");
				data.setDataBlock(block.getBytes(1, (int) block.length()));
				list.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (preStat != null) {
					preStat.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 释放连接
			JDBCHelper.closeDBConnection(conn);
		}
		return list;
		
	}

}
