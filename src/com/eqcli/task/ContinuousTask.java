package com.eqcli.task;

import java.util.LinkedList;

import com.eqcli.application.ClientApp;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.data.WavefData;

import io.netty.channel.ChannelHandlerContext;

/** 连续传输模式 */
public class ContinuousTask extends TransTask {

	/** 发送队列 */
	private LinkedList<WavefData> sendQueue;
	private int queueCapacity = 20;   //容量
	private WavefDataDao dao;
	private int speed = 2000;     //发送速率 单位ms

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
		// System.out.println("连续传输运行前");
		while (ClientApp.transMode == Constant.MODE_CONTINUOUS) {
			try {
				// System.out.println("连续传输运行中");

				// 发送队列大小小于队列阈值时(5),从数据库中获取
				if (sendQueue.size() < 5) {
					int startid = 0;
					if (sendQueue.isEmpty()) {
						startid = dao.getLastId();
						startid = startid < 0 ? 0 : startid; 
					} else {
						startid = sendQueue.getLast().getId() + 1;
					}
					Reloading(startid, queueCapacity - 5);
				}
				if (!sendQueue.isEmpty()) {
					// 将队列中的第一个数据对象发送出去
					send();
				}
				Thread.sleep(speed);    //发送间隔
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("连续传输运行结束");
	}

	/** 向发送队列中加载数据 */
	private void Reloading(int start, int count) {
		sendQueue.addAll(dao.get(start, count));
	}

	/** 发送到服务端 */
	private void send() {
		context.writeAndFlush(DataBuilder.buildWavefDataMsg(MsgConstant.TYPE_WC, sendQueue.removeFirst()));
	}
}