package com.eqcli.task;

import java.util.Random;

import com.eqcli.application.EqClient;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;

import io.netty.channel.ChannelHandlerContext;

/**
 * 触发传波形数据模式
 * 未完成
 *
 */
public class TrgWithWaveTask extends TransTask {

	private short timeTick;
	public TrgWithWaveTask(ChannelHandlerContext _ctx){
		this.context = _ctx;
	}
	@Override
	public void run() {
		if(context == null){
			return;
		}

	}

}
