package com.eqcli.task;

import java.util.concurrent.TimeUnit;

import com.eqcli.dao.WavefDataDao;
import com.eqcli.util.DataBuilder;

/**
 * 模拟波形数据产生任务 按照一定频率产生数据,并存入数据库
 * 
 */
public class DataCreatorTask implements Runnable {

	private WavefDataDao dao;
	private int packetid;
	private int speed = 200;   //数据产生速率(单位ms)

	public DataCreatorTask() {
		dao = new WavefDataDao();
		packetid = getPacketId();
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(speed);   //产生速率 1秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dao.save(DataBuilder.buildWavefData(packetid++));
		}
	}
	
	private int getPacketId(){
		
		return dao.getLastId();
	}

}
