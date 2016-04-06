package com.eqsys.msg;

import java.io.Serializable;

public class TriggerReq extends CommandReq{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private short subCommand;  			//子控制命令 
	private long startTime;			//触发开始时间  绝对UTC时间
	private long endTime;			//触发结束时间  绝对UTC时间
	
	//getter and setter
//	public short getSubCommand() {
//		return subCommand;
//	}
//	public void setSubCommand(short subCommand) {
//		this.subCommand = subCommand;
//	}
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
