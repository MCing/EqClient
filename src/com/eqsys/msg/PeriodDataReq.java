package com.eqsys.msg;

import java.io.Serializable;

public class PeriodDataReq extends CommandReq{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private short subCommand;    	//子控制命令  常量2
	private long timeCode;			//时间码    传输波形数据起始时间码（绝对UTC时间）
	private int period;				//时间长度  需传输的波形数据时间长度，单位为秒
	
	//getter and setter
//	public short getSubCommand() {
//		return subCommand;
//	}
//	public void setSubCommand(short subCommand) {
//		this.subCommand = subCommand;
//	}
	public long getTimeCode() {
		return timeCode;
	}
	public void setTimeCode(long timeCode) {
		this.timeCode = timeCode;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
}
