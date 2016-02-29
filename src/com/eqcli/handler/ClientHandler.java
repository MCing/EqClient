package com.eqcli.handler;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.eqcli.application.ClientApp;
import com.eqcli.task.ContinuousTask;
import com.eqcli.task.TrgNoWaveTask;
import com.eqcli.task.TrgWithWaveTask;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqsys.msg.BaseCmdMsg;
import com.eqsys.msg.BaseMsg;
import com.eqsys.msg.CtrlCmdRspMsg;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.RegRspMsg;
import com.eqsys.msg.TransModeMsg;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.ScheduledFuture;

public class ClientHandler extends ChannelHandlerAdapter {
	
	private Logger log = Logger.getLogger(ClientHandler.class);

	private ClientApp client;
	private boolean isRegRsped; // 是否收到注册回复,无论是否成功,收到后都为true
								// deprecated:注册是否成功(认证失败或获取认证超时都会到时失败)标识
	private int lastPacketId;

	private ChannelHandlerContext context;

	public ClientHandler(ClientApp client) {
		this.client = client;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
//		System.out.println("channel active:" + ctx.channel().toString());
		send(ctx, DataBuilder.buildRegMsg());  // 发送注册信息包
		isRegRsped = false;

		// 发送完注册信息后也要设定超时,注册超时
		EventExecutor loop = ctx.executor();
		ScheduledFuture fu = loop.schedule(new ReconnectTask(), 10, TimeUnit.SECONDS);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		//企图重连,但是无法感知与服务端断开连接
//		log.info("与服务器断开连接");
//		isRegRsped = false;
//		client.reconnect();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		BaseMsg revMsg = (BaseMsg) msg;
		String msgType = revMsg.getMsgType();
		log.info("收到数据包类型:"+msgType);
		if (MsgConstant.TYPE_RR.equals(msgType)) { // 注册回应消息

			isRegRsped = true;
			RegRspMsg rrMsg = (RegRspMsg) msg;
			if (rrMsg.getAuthenState() == MsgConstant.REG_SUCCESS) { // 注册成功
				log.info("向服务器注册成功");
				this.context = ctx;
				lastPacketId = rrMsg.getLastPacketNo();

			} else {

				// 注册失败 提示
				//System.out.println("注册失败");
				log.error("向服务器注册失败");
				// 注册失败之后....
				// 参数错误,修改连接参数
				// 关闭定时器
			}

		} else if (MsgConstant.TYPE_CC.equals(msgType)) { // 控制消息

			handleCCMsg(msg, ctx);
		}
	}

	/**
	 * 处理连接异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.close();
	}

	/**
	 * 处理服务器控制命令 not finish
	 */
	private void handleCCMsg(Object msg, ChannelHandlerContext ctx) {

		BaseCmdMsg ccMsg = (BaseCmdMsg) msg;

		short state = 0;
		switch (ccMsg.getSubCommand()) {
		// 对不同的控制命令更改对应的状态,然后返回状态值
		case MsgConstant.CMD_TRANSMODE: {
			TransModeMsg submsg = (TransModeMsg) msg;
			// state = handle(); //进一步处理返回状态 告知服务器
			System.out.println("传输模式控制包");
			short mode = 0;
			switch (submsg.getSubTransMode()) {
			case 1:
				mode = Constant.MODE_CONTINUOUS;
				break;
			case 2:
				mode = Constant.MODE_TRG_WAVE;
				break;
			case 3:
				mode = Constant.MODE_TRG_NWAV;
				break;
				default:
					mode = ClientApp.transMode;
					break;
			}
			
			switchTransMode(mode, ctx);
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

		CtrlCmdRspMsg ccRMsg = DataBuilder.buildCtrlRspMsg(ccMsg.getPacketId(), state, "okok",
				ccMsg.getSubCommand());

//		ctx.writeAndFlush(ccRMsg);
		send(ctx, ccRMsg);
	}

	/**
	 * 重连任务
	 *
	 */
	private class ReconnectTask implements Runnable {

		@Override
		public void run() {

			// 如果连接失败则重连
			if (!isRegRsped) {
				log.info("向服务器注册超时,启动重连");
				client.reconnect();
			} else {
//				System.out.println("定时任务 无需重连");
			}
		}
	}

	/**
	 * 切换传输模式
	 * @param mode 要切换的传输模式
	 * @param ctx
	 */
	private void switchTransMode(short mode, ChannelHandlerContext ctx) {
		ClientApp.transMode = mode;
		EventExecutor loop = ctx.executor();

		// 延时后启动相应模式,延时是为了让正在运行模式的任务处理完
		switch (mode) {
		case Constant.MODE_CONTINUOUS:
			loop.schedule(new ContinuousTask(ctx, lastPacketId), 100, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_NWAV:
			loop.schedule(new TrgNoWaveTask(ctx), 100, TimeUnit.MILLISECONDS);
			break;
		case Constant.MODE_TRG_WAVE:
			loop.schedule(new TrgWithWaveTask(ctx), 100, TimeUnit.MILLISECONDS);
			break;
		default:     //空闲模式
			break;
		}

	}
	
	
	/** 发送数据出口
	 * 
	 * @param ctx   ChannelHandlerContext对象
	 * @param msg   数据包对象
	 */
	private void send(ChannelHandlerContext ctx, Object msg){
		
		if(ctx != null && msg != null){
			ctx.writeAndFlush(msg);
		}
	}
}
