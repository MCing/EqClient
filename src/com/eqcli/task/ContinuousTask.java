package com.eqcli.task;

import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.dao.WavefDataDao;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.data.WavefData;

/** 连续传输模式 */
public class ContinuousTask extends TransTask {

	private Logger log = Logger.getLogger(ContinuousTask.class);

	/** 发送队列 */
	private LinkedList<WavefData> sendQueue;
	private int queueCapacity = 20; // 容量
	private WavefDataDao dao;
	private int lastSendedId = 0;

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
	}

	@Override
	public void run() {
		if (context == null) {
			return;
		}
		try {
			// 发送队列大小小于队列阈值时(5),从数据库中获取
			if (sendQueue.size() < 5) {
				int startid = 0;
				if (sendQueue.isEmpty()) {
						
//					System.err.println("lastSendedId:"+lastSendedId);
					startid = lastSendedId == 0 ? 0 : lastSendedId + 1;
				} else {
					startid = sendQueue.getLast().getId() + 1;

				}
				Reloading(startid, queueCapacity - 2);
			}
			if (!sendQueue.isEmpty()) {
				// 将队列中的第一个数据对象发送出去
				send();
			} else {
				log.error("无数据发送");
			}
		} catch (Exception e) {
			log.error("连续传输模式错误:" + e.getMessage());
		}
	}

	/** 向发送队列中加载数据 */
	private void Reloading(int start, int count) {

		List<WavefData> list = dao.get(start, count);
		sendQueue.addAll(list);
	}

	/** 发送到服务端 */
	private void send() {

		WavefData data = sendQueue.removeFirst();
		lastSendedId = data.getId();
		log.info("发送波形数据:" + lastSendedId);
		context.writeAndFlush(DataBuilder.buildWavefDataMsg(MsgConstant.TYPE_WC, data));
	}
}