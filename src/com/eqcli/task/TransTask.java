package com.eqcli.task;

import io.netty.channel.ChannelHandlerContext;

public abstract class TransTask implements Runnable {
	
	protected ChannelHandlerContext context;
	
	@Override
	public abstract void run();

}
