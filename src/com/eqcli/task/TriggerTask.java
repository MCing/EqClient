package com.eqcli.task;

import java.util.concurrent.TimeUnit;

import com.eqcli.util.DataBuilder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * 检测触发信号的任务
 *
 */
public class TriggerTask extends TransTask {
	
	//状态信息包序号
	private static int statePacketId = 1;
	//提供给初始化模块,初始化状态信息包序号
	public static void initStatePId(int id){
		statePacketId = id;
	}
	
	private ScheduledFuture stateTaskFuture;
	public TriggerTask(ChannelHandlerContext ctx){
		
		this.context = ctx;
	}
	
	@Override
	public void run(){
		
		//if not detected
		if(stateTaskFuture == null){
			
			stateTaskFuture = context.executor().scheduleAtFixedRate(new SendStateTask(), 100, 10000, TimeUnit.MILLISECONDS);
		}
		context.executor().schedule(this, 100, TimeUnit.MILLISECONDS);
	}
	
	
	/** 
	 * 发送状态信息任务
	 *
	 */
	private class SendStateTask implements Runnable{
		
		@Override
		public void run(){
			
			context.writeAndFlush(DataBuilder.buildStatusDataMsg(statePacketId++));
			
		}
	}

}
