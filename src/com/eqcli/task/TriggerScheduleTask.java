package com.eqcli.task;

import com.eqcli.simulation.TriggerDetection;
import com.eqcli.util.UTCTimeUtil;

/**
 *	触发控制任务
 */
public class TriggerScheduleTask implements Runnable{

	private boolean switchSignal;	//触发控制信号,true:开   false:关
	public TriggerScheduleTask(boolean switchSignal){
		this.switchSignal = switchSignal;
	}
	@Override
	public void run() {
		System.err.println(UTCTimeUtil.timeFormat1(System.currentTimeMillis())+"-----运行 TriggerScheduleTask:"+switchSignal);
		TriggerDetection.setCtrlSignal(switchSignal);
	}

}
