package com.eqcli.simulation;

import com.eqcli.application.EqClient;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqcli.util.JDBCHelper;
import com.eqcli.util.ParseUtil;
import com.eqcli.util.UTCTimeUtil;
import com.eqsys.msg.MsgConstant;

/**
 * 模拟波形数据产生任务 按照一定频率产生数据,并存入数据库
 * 
 */
public class DataCreatorTask implements Runnable {

	private WavefDataDao dao;
	private int packetid = -1;
	// private int speed = 200; //数据产生速率(单位ms)
	// 数据条目计数器
	private static int counter;

	public DataCreatorTask() {
		dao = new WavefDataDao();
		packetid = getPacketId();
	}

	@Override
	public void run() {
		dao.save(DataBuilder.buildWavefData(++packetid));
		counter++;
		DataReport data = new DataReport();
		data.setTime(UTCTimeUtil.getSimpleTimeStr(System.currentTimeMillis()));
		data.setId(packetid);
		data.setType("连续波形数据");
		EqClient.dataList.add(data);
	}

	private int getPacketId() {

		return dao.getLastId();
	}

	public static int getCount() {
		return counter;
	}
}
