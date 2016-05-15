package com.eqcli.handler;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.EqClient;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqcli.util.LogUtil;
import com.eqsys.msg.EqMessage;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.RegResp;
import com.mysql.jdbc.Constants;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * 处理注册响应包
 * 处理注册超时重连 发送心跳请求消息
 */
public class RegReqHandler extends ChannelHandlerAdapter {

	private Logger log = Logger.getLogger(RegReqHandler.class);
	// private boolean isRegister; //是否注册成功
	private EqClient client;
	private static ScheduledFuture reconnectTask;

	public RegReqHandler(EqClient client) {
		this.client = client;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {


		send(ctx, DataBuilder.buildRegMsg()); // 发送注册信息包
		// 发送完注册信息后也要设定超时,注册超时(超时时间的设定与网络情况关系很大)
		reconnectTask = ctx.executor().schedule(new ReconnectTask(ctx), 180,
				TimeUnit.SECONDS);
	};

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		client.updateUI(Constant.UICODE_STATE, false);
		interruptAndReconnect(ctx);
		ctx.fireChannelInactive();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		EqMessage eqMsg = (EqMessage) msg;
		String msgType = eqMsg.getHeader().getMsgType();
		log.info("收到数据包类型:" + msgType);
		if (MsgConstant.TYPE_RR.equals(msgType)) { // 注册回应消息

			RegResp bodyMsg = (RegResp) eqMsg.getBody();
			if (bodyMsg.getAuthenState() == MsgConstant.REG_SUCCESS) { // 注册成功

				log.info("注册成功");
				LogUtil.sysLog("连接台网成功");
				client.updateUI(Constant.UICODE_STATE, true);
				// 将注册应答包消息中的一些参数(上次包序列号)透传到CtrlRespHandler
				ctx.fireChannelRead(msg);
			} else {
				LogUtil.sysLog("连接台网失败");
				log.error("向服务器注册失败,认证失败");
				// 注册失败之后....
				// 参数错误,修改连接参数
				// 关闭定时器
				ctx.close();
			}
			if (reconnectTask != null) {
				reconnectTask.cancel(true);
				reconnectTask = null;
			}

		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
//		if (cause instanceof ReadTimeoutException) {
//
//			interruptAndReconnect(ctx);
//			ctx.close();
//		}
		log.error("异常: "+ cause.toString());
		// 无论是否是超时异常,都要透传该异常
		ctx.fireExceptionCaught(cause);
	}


	/**
	 * 重连任务
	 *
	 */
	private class ReconnectTask implements Runnable {

		private ChannelHandlerContext ctx;
		public ReconnectTask(ChannelHandlerContext ctx){
			this.ctx = ctx;
		}
		@Override
		public void run() {
			
			client.reconnect();
		}
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
	 * 中断后重连
	 * 
	 * @param ctx
	 */
	private void interruptAndReconnect(ChannelHandlerContext ctx) {

		// 防止定时重连任务重复开启
		if(reconnectTask != null){
			reconnectTask.cancel(true);
			reconnectTask = null;
		}
		log.error("中断重连");
		reconnectTask = ctx.executor().schedule(new ReconnectTask(ctx), 10,
				TimeUnit.SECONDS);
	}
	
}
