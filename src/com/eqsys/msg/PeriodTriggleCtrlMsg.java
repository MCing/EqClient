package com.eqsys.msg;

/**
 * 6.时间段触发控制包
 * 发送端：服务器
 * 标识符：CC
 *
 */
public class PeriodTriggleCtrlMsg extends BaseCmdMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long startTime;			//触发开始时间  绝对UTC时间
	private long endTime;			//触发结束时间  绝对UTC时间
	
	//getter and setter
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
}
