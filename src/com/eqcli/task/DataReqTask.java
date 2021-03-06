package com.eqcli.task;

import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.EqClient;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.simulation.TriggerDetection;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.data.WavefData;

/** 
 * 时间段波形数据申请
 *
 */
public class DataReqTask extends TransTask {
	
	private Logger log = Logger.getLogger(ContinuousTask.class);

	/** 发送队列 */
	private LinkedList<WavefData> sendQueue;

	public DataReqTask(ChannelHandlerContext _ctx, long starttime, long endtime) {

		this.context = _ctx;
		sendQueue = new LinkedList<WavefData>();
		sendQueue.addAll(WavefDataDao.get(starttime, endtime));
		System.err.println("DataReqTask size:"+sendQueue.size());
	}

	@Override
	public void run() {
		
		if(!sendQueue.isEmpty() && (EqClient.currTransMode == Constant.MODE_TRG_NWAV || EqClient.currTransMode == Constant.MODE_TRG_WAVE) && !TriggerDetection.detect()){
			WavefData data = sendQueue.removeFirst();
			context.writeAndFlush(DataBuilder.buildWavefDataMsg(MsgConstant.TYPE_WS, data));
			System.err.println("发送时间段数据------------id:"+data.getId());
		}
		//自启动
		if(!sendQueue.isEmpty() && context.channel().isActive()){
			context.executor().schedule(this, 1000, TimeUnit.MILLISECONDS);
		}else{
			System.err.println("DataReqTask is closed");
		}
	}

}
