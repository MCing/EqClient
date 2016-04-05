package com.eqcli.task;

import java.util.Random;

import com.eqcli.application.EqClient;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;

import io.netty.channel.ChannelHandlerContext;

/**
 * 触发不传输波形数据模式
 * 未完成
 *
 */
public class TrgNoWaveTask extends TransTask {

	private short timeTick;
	public TrgNoWaveTask(ChannelHandlerContext _ctx){
		this.context = _ctx;
	}
	@Override
	public void run() {
		if(context == null){
			return;
		}
		while(EqClient.transMode == Constant.MODE_TRG_NWAV){		
			//未完成
			
			try {
				//if(!isTrigger)
				if(timeTick%10 == 0){
					context.writeAndFlush(DataBuilder.buildStatusData(new Random().nextInt(10000)));
					timeTick = 1;
				}else{
					Thread.sleep(1000);   //等待 10秒 = 1秒  * 10;
					timeTick++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
