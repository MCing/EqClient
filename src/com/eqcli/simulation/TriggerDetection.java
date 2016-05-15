package com.eqcli.simulation;

import com.eqcli.util.DataBuilder;
import com.eqsys.msg.data.StatusData;
import com.eqsys.msg.data.TrgData;

/** 
 * 模拟触发检测
 *
 */
public class TriggerDetection {
	
	private static volatile boolean triggerSignal = false;	//触发信号
	private static volatile boolean ctrlSignal = false;		//触发控制信号
	

	/** 模拟检测触发接口 
	 *  由触发信号和触发控制信号决定结果
	 */
	public static boolean detect(){
		
		return triggerSignal || ctrlSignal;
	}
	
	/** 获取触发模式下的状态数据接口 */
	public static StatusData getStatusData(int id){
		
		return DataBuilder.buildStatusData(id);
	}
	
	/** 获取触发时的触发信息数据接口 */
	public static TrgData getTrgData(int id){
		
		return DataBuilder.buildTrgData(id);
	}
	/** 切换触发信号 */
	public static void toggleTriggerSignal(){
		triggerSignal = !triggerSignal;
	}
	/** 设定控制信号 */
	public static void setCtrlSignal(boolean value){
		ctrlSignal = value;
	}
	public static void initTriggerDetect(){
		triggerSignal = ctrlSignal = false;
	}
	
}
