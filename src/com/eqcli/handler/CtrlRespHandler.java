package com.eqcli.handler;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.EqClient;
import com.eqcli.task.ContinuousTask;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.CommandReq;
import com.eqsys.msg.EqMessage;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.RegResp;
import com.eqsys.msg.TransModeReq;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

public class CtrlRespHandler extends ChannelHandlerAdapter {

	private Logger log = Logger.getLogger(CtrlRespHandler.class);

	private ScheduledFuture transTask;

	private int lastPacketId;

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
			switch (bodyMsg.getSubCommand()) {
			// 对不同的控制命令更改对应的状态,然后返回状态值
			case MsgConstant.CMD_TRANSMODE: {
				// state = handle(); //进一步处理返回状态 告知服务器
				TransModeReq submsg = (TransModeReq) bodyMsg;
				switchTransMode(submsg.getSubTransMode(), ctx);
			}
				break;
			case MsgConstant.CMD_PERIODDATA:
				System.out.println("时间段数据申请包");
				break;
			case MsgConstant.CMD_TRGPRIOD:
				System.out.println("触发阈值设定控制包");
				break;
			case MsgConstant.CMD_TRGTHRESHOLD:
				System.out.println("时间段触发控制包");
				break;
			default:
				break;
			}

			// test  状态回应
			EqMessage ccRMsg = DataBuilder.buildCtrlRspMsg(
					eqMsg.getHeader().getPid(), state, "okok", bodyMsg.getSubCommand());

			send(ctx, ccRMsg);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
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

	/**
	 * 切换传输模式
	 * 
	 * @param mode
	 *            要切换的传输模式 -1:空闲模式 0:不改变模式 1:连续波形 2:触发传输传波形 3:触发传输不传波形
	 * @param ctx
	 */
	private void switchTransMode(short mode, ChannelHandlerContext ctx) {

		mode = (mode == 0) ? EqClient.transMode : mode;
		if (mode != Constant.MODE_IDLE) {
			EqClient.transMode = mode;
		}
		// 延时后启动相应模式,延时是为了让正在运行模式的任务处理完
		switch (mode) {
		case Constant.MODE_CONTINUOUS:
			if(transTask != null && transTask.isCancelled()){
				boolean ret = transTask.cancel(true);
				System.err.println("switchTransMode cancel result:"+ret);
			}
			transTask = ctx.executor().scheduleAtFixedRate(new ContinuousTask(ctx,
					lastPacketId), 10, 500, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_WAVE:
			if(transTask != null && transTask.isCancelled()){
				boolean ret = transTask.cancel(true);
				System.err.println("switchTransMode cancel result:"+ret);
			}
			transTask = ctx.executor().scheduleAtFixedRate(new ContinuousTask(ctx,
					lastPacketId), 10, 500, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_NWAV:
			if(transTask != null && transTask.isCancelled()){
				boolean ret = transTask.cancel(true);
				System.err.println("switchTransMode cancel result:"+ret);
			}
			transTask = ctx.executor().scheduleAtFixedRate(new ContinuousTask(ctx,
					lastPacketId), 10, 500, TimeUnit.MILLISECONDS);
			break;
		default: // 空闲模式
			if (transTask != null) {
				transTask.cancel(true);
			}
			transTask = null;
			break;
		}

	}
}
