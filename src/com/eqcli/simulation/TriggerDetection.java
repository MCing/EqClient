package com.eqcli.simulation;

import com.eqcli.util.DataBuilder;
import com.eqsys.msg.data.StatusData;
import com.eqsys.msg.data.TrgData;

/** 
 * 模拟触发检测
 *
 */
public class TriggerDetection {
	
	public static volatile boolean triggerSignal = false;

	/** 模拟检测触发接口 */
	public static boolean detect(){
		
		return triggerSignal;
	}
	
	/** 获取触发模式下的状态数据接口 */
	public static StatusData getStatusData(int id){
		
		return DataBuilder.buildStatusData(id);
	}
	
	/** 获取触发时的触发信息数据接口 */
	public static TrgData getTrgData(int id){
		
		return DataBuilder.buildTrgData(id);
	}
	
	
	
}
