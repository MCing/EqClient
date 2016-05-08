package com.eqcli.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.EqClient;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.simulation.TriggerDetection;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqcli.util.ParseUtil;
import com.eqcli.util.SysConfig;
import com.eqcli.util.UTCTimeUtil;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.data.StatusData;
import com.eqsys.msg.data.TrgData;
import com.eqsys.msg.data.WavefData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * 触发传波形模式
 *
 */
public class TriggerTask extends TransTask {

	private Logger log = Logger.getLogger(TriggerTask.class);

	private ScheduledFuture stateFuture; // 发送状态信息任务
	private ScheduledFuture sendDataFuture; // 发送触发数据任务
	private boolean isTrigger;
	private WavefDataDao dao;
	private LinkedList<WavefData> wavefDataQueue; // 发送队列，用于发送触发时的波形数据和触发信息
	private LinkedList<TrgData> trgDataQueue; // 发送队列，用于发送触发时的波形数据和触发信息
	private boolean withWavefData; // 触发是否传输波形数据
	// 包序号
	private int wavefDataPid;
	private int statusDataPid;
	private int triDataPid;

	public TriggerTask(ChannelHandlerContext _ctx, boolean withWavefData) {
		dao = new WavefDataDao();
		this.withWavefData = withWavefData;
		this.context = _ctx;
		trgDataQueue = new LinkedList<TrgData>();
		wavefDataQueue = new LinkedList<WavefData>();

		statusDataPid = SysConfig.getStatusDataPid() + 1;
		triDataPid = SysConfig.getTriDataPid() + 1;
	}

	@Override
	public void run() {
		if (context == null) {
			return;
		}

		if (TriggerDetection.detect()) { // 检测到触发事件

			// 停止状态信息传输任务
			cancelFuture(stateFuture);
			if (!isTrigger) { // 第一次触发

				if (withWavefData) {
					List<WavefData> list = dao.getLast30(UTCTimeUtil.getCurrUTCTime());

					wavefDataPid = list.get(list.size() - 1).getId();
					log.error("第一次触发后的触发波形 size:" + list.size());
					// 加锁。。。。。。。。未完成
					wavefDataQueue.addAll(list);
				}

				trgDataQueue.add(TriggerDetection.getTrgData(triDataPid++));
				// 开启任务
				sendDataFuture = context.executor().scheduleAtFixedRate(new SendDataTask(), 100, 500,
						TimeUnit.MILLISECONDS);

				isTrigger = true;

			} else {
				// 获取触发波形数据 ??
				if (withWavefData) {
					List<WavefData> list = dao.getTrgData(wavefDataPid, UTCTimeUtil.getCurrUTCTime());

					wavefDataPid = list.get(list.size() - 1).getId();
					log.error("二次触发后的触发波形 size:" + list.size());
					// 加锁。。。。。。。。未完成
					wavefDataQueue.addAll(list);
				}
				// 获取触发信息数据 ??
				trgDataQueue.add(TriggerDetection.getTrgData(triDataPid++));
			}

		} else { // 未触发,开启发送状态信息任务

			isTrigger = false;
			if (stateFuture == null || stateFuture.isCancelled()) {
				// 间隔10秒
				stateFuture = context.executor().scheduleAtFixedRate(new SendStateTask(), 100, 10000,
						TimeUnit.MILLISECONDS);
			}
		}

	}

	/** 结束内部任务 ,即发送状态信息任务和发送触发信息(和触发波形)数据任务 */
	public void shutdownITask() {

		trgDataQueue.clear();
		wavefDataQueue.clear();
		cancelFuture(stateFuture);
		cancelFuture(sendDataFuture);
		SysConfig.saveTriggerPid(statusDataPid - 1, triDataPid - 1);
	}

	private void cancelFuture(ScheduledFuture future) {

		if (future != null && !future.isDone()) {
			future.cancel(true);
		}
	}

	/**
	 * 传输状态信息线程
	 *
	 */
	private class SendStateTask implements Runnable {

		@Override
		public void run() {
			StatusData data = TriggerDetection.getStatusData(statusDataPid++);
			send(MsgConstant.TYPE_SI, data);
			log.error("发送状态信息数据  id:" + data.getId());
		}

	}

	/**
	 * 传出波形数据和触发信息线程 发送队列驱动数据的发送
	 */
	private class SendDataTask implements Runnable {

		@Override
		public void run() {

			if (!trgDataQueue.isEmpty()) { // 发送触发信息数据
				TrgData trgData = trgDataQueue.removeFirst();
				send(MsgConstant.TYPE_TI, trgData);
				log.error("发送触发信息数据  id:" + trgData.getId());
			}

			if (withWavefData && !wavefDataQueue.isEmpty()) { // 发送触发波形数据
				WavefData wavefData = wavefDataQueue.removeFirst();
				send(MsgConstant.TYPE_WT, wavefData);
				log.error("发送触发波形数据  id:" + wavefData.getId());
			}
		}

	}

	/** 发送到服务端 */
	private void send(String type, Object data) {
		Object msg = null;
		if (MsgConstant.TYPE_WT.equals(type)) {
			msg = DataBuilder.buildWavefDataMsg(MsgConstant.TYPE_WT, (WavefData) data);
		} else if (MsgConstant.TYPE_SI.equals(type)) {
			msg = DataBuilder.buildStatusDataMsg((StatusData) data);
		} else if (MsgConstant.TYPE_TI.equals(type)) {
			msg = DataBuilder.buildTriggleMsg((TrgData) data);
		}
		if (msg != null) {

			context.writeAndFlush(msg);
		}
	}
}
