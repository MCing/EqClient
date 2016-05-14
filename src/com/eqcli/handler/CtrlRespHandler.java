package com.eqcli.handler;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.EqClient;
import com.eqcli.simulation.DataReport;
import com.eqcli.task.ContinuousTask;
import com.eqcli.task.DataReqTask;
import com.eqcli.task.TriggerTask;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqcli.util.ParseUtil;
import com.eqsys.msg.CommandReq;
import com.eqsys.msg.EqMessage;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.PeriodDataReq;
import com.eqsys.msg.RegResp;
import com.eqsys.msg.ThresholdReq;
import com.eqsys.msg.TransModeReq;
import com.mysql.jdbc.Constants;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

public class CtrlRespHandler extends ChannelHandlerAdapter {

	private Logger log = Logger.getLogger(CtrlRespHandler.class);
	private EqClient client;
	private ScheduledFuture transTask;
	private ScheduledFuture dataReqTask;

	private int lastPacketId;
	
	public CtrlRespHandler(EqClient client) {
		this.client = client;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		EqMessage eqMsg = (EqMessage) msg;
		String msgType = eqMsg.getHeader().getMsgType();
		if (MsgConstant.TYPE_RR.equals(msgType)) {

			RegResp bodyMsg = (RegResp) eqMsg.getBody();
			lastPacketId = bodyMsg.getLastPacketNo();
		} else if (MsgConstant.TYPE_CC.equals(msgType)) {

		   CommandReq bodyMsg = (CommandReq) eqMsg.getBody();
			short state = 0;
			String respMsg = null;
			switch (bodyMsg.getSubCommand()) {
			// 对不同的控制命令更改对应的状态,然后返回状态值
			case MsgConstant.CMD_TRANSMODE: {
				// state = handle(); //进一步处理返回状态 告知服务器
				TransModeReq submsg = (TransModeReq) bodyMsg;
				short tmpMode = submsg.getSubTransMode();
				if(tmpMode == 0) { tmpMode = EqClient.defTransMode; }
				switchTransMode(tmpMode, ctx);
				respMsg = "切换到"+ParseUtil.parseTransMode(tmpMode);
				client.updateUI(Constant.UICODE_MODE, ParseUtil.parseTransMode(tmpMode));
			}
				break;
			case MsgConstant.CMD_PERIODDATA:{
				PeriodDataReq submsg = (PeriodDataReq) bodyMsg;
				respMsg = "时间段数据申请响应";
				System.out.println("时间段数据申请包");
				long starttime = submsg.getTimeCode();
				long endtime = starttime+submsg.getPeriod();
				dataReqTask = ctx.executor().scheduleAtFixedRate(new DataReqTask(ctx, starttime, endtime), 1000, 1000, TimeUnit.MILLISECONDS);
			}
				break;
			case MsgConstant.CMD_TRGPRIOD:
				ThresholdReq thrmsg = (ThresholdReq)bodyMsg;
				System.out.println("触发阈值设定控制包");
				client.updateUI(Constant.UICODE_THREHOLD, thrmsg.getTriggleThreshold());
				break;
			case MsgConstant.CMD_TRGTHRESHOLD:
				System.out.println("时间段触发控制包");
				break;
			default:
				break;
			}

			// test  状态回应
			EqMessage ccRMsg = DataBuilder.buildCtrlRspMsg(
					eqMsg.getHeader().getPid(), state, respMsg, bodyMsg.getSubCommand());

			send(ctx, ccRMsg);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if(dataReqTask != null){
			dataReqTask.cancel(true);
			dataReqTask = null;
		}
		switchTransMode(Constant.MODE_IDLE, ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		String errStr = cause.getMessage();
		if (errStr == null) {
			cause.printStackTrace();
		} else {
			log.error(errStr);
		}
		ctx.close();
	}

	/**
	 * 发送数据出口
	 * 
	 * @param ctx
	 *            ChannelHandlerContext对象
	 * @param msg
	 *            数据包对象
	 */
	private void send(ChannelHandlerContext ctx, Object msg) {

		if (ctx != null && msg != null) {
			ChannelFuture f = ctx.writeAndFlush(msg);
		}
	}

	
	private TriggerTask triggerTask;
	/**
	 * 切换传输模式
	 * 
	 * @param mode
	 *            要切换的传输模式 -1:空闲模式 0:不改变模式 1:连续波形 2:触发传输传波形 3:触发传输不传波形
	 * @param ctx
	 * 
	 * 空闲模式---------连续    使用lastPacketId
	 * 触发模式（两种）----连续   不续传
	 * 
	 */
	private void switchTransMode(short mode, ChannelHandlerContext ctx) {

		
		// 延时后启动相应模式,延时是为了让正在运行模式的任务处理完
		prepareTask();
		switch (mode) {
		case Constant.MODE_CONTINUOUS:
			int startPid = lastPacketId;
			//从触发模式转到连续传输模式，包序号不续传
			if(EqClient.currTransMode == Constant.MODE_TRG_NWAV || EqClient.currTransMode == Constant.MODE_TRG_WAVE){
				startPid = 0xffffffff;
			}
			transTask = ctx.executor().scheduleAtFixedRate(new ContinuousTask(ctx,
					startPid), 500, 500, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_WAVE:
			triggerTask = new TriggerTask(ctx, true);
			transTask = ctx.executor().scheduleAtFixedRate(triggerTask, 10, 1000, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_NWAV:
			triggerTask = new TriggerTask(ctx, false);
			transTask = ctx.executor().scheduleAtFixedRate(triggerTask, 10, 1000, TimeUnit.MILLISECONDS);
			break;
		default: // 空闲模式
			//断开连接切换到空闲模式前，保存断开前的传输模式到默认模式中，下次重新连接时依然采用断开连接前的模式
			if (EqClient.currTransMode != Constant.MODE_IDLE) {
				EqClient.defTransMode = EqClient.currTransMode;
			}
			break;
		}
		EqClient.currTransMode = mode;
	}
	
	/** 切换传输模式前的准备,关闭开启的任务 */
	private void prepareTask(){
		if(triggerTask != null){
			triggerTask.shutdownITask();
			triggerTask = null;
		}
		if(transTask != null && !transTask.isCancelled()){
			transTask.cancel(true);
			transTask = null;
		}
	}
}
