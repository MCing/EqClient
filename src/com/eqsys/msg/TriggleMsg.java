package com.eqsys.msg;

import com.eqsys.msg.data.TrgData;

/**
 * 9.触发信息包
 * 发送端:客户端
 * 标识符:TI
 *
 *	未添加 getter and setter 方法
 */
public class TriggleMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TrgData triggerData;

	public TrgData getTriggerData() {
		return triggerData;
	}

	public void setTriggerData(TrgData triggerData) {
		this.triggerData = triggerData;
	}
}
