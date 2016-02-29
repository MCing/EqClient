package com.eqcli.task;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.eqcli.application.ClientApp;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.data.WavefData;

import io.netty.channel.ChannelHandlerContext;

/** 连续传输模式 */
public class ContinuousTask extends TransTask {
	
	private Logger log = Logger.getLogger(ContinuousTask.class);

	/** 发送队列 */
	private LinkedList<WavefData> sendQueue;
	private int queueCapacity = 20;   //容量
	private WavefDataDao dao;
	private int speed = 500;     //发送速率 单位ms
	private int lastSendedId = 0;

	private int tmp;
	public ContinuousTask(ChannelHandlerContext _ctx, int packetid) {

		dao = new WavefDataDao();
		sendQueue = new LinkedList<WavefData>();
		if (packetid == 0xffffffff) { // 表示不继传,从最新的10个数据包开始
			packetid = dao.getLastId() - 10;
			if (packetid < 0) {
				packetid = 0;
			}
		}
		Reloading(packetid, queueCapacity);
		this.context = _ctx;
		System.err.println("ContinuousTask context:"+context.toString());
	}

	@Override
	public void run() {
		if (context == null) {
			return;
		}
		while (ClientApp.transMode == Constant.MODE_CONTINUOUS) {
			try {
				log.info("连续传输模式1:"+tmp);
				
				// 发送队列大小小于队列阈值时(5),从数据库中获取
				if (sendQueue.size() < 5) {
					int startid = 0;
					if (sendQueue.isEmpty()) {
						//bug
//						startid = dao.getLastId();
//						startid = startid < 0 ? 0 : startid; 
						startid = lastSendedId == 0 ? 0 : lastSendedId + 1;
					} else {
						startid = sendQueue.getLast().getId() + 1;
						
					}
					Reloading(startid, queueCapacity - 2);
					log.info("连续传输模式2:"+tmp);
				}
				if (!sendQueue.isEmpty()) {
					// 将队列中的第一个数据对象发送出去
					send();
				}else{
					log.error("发送队列为空");
				}
				log.info("连续传输模式3:"+tmp++);
				Thread.sleep(speed);    //发送间隔
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch(Exception e){
				log.error("连续传输模式错误:"+e.getMessage());
			}
		}
		log.info("结束连续传输模式");
	}

	/** 向发送队列中加载数据 */
	private void Reloading(int start, int count) {
		sendQueue.addAll(dao.get(start, count));
	}

	/** 发送到服务端 */
	private void send() {
		WavefData data = sendQueue.removeFirst();
		lastSendedId = data.getId();
		//log.info("发送波形数据:"+lastSendedId);
		context.writeAndFlush(DataBuilder.buildWavefDataMsg(MsgConstant.TYPE_WC, data));
	}
}