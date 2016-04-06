package com.eqsys.msg;

import java.io.Serializable;

public class ThresholdReq extends CommandReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private short subCommand;  			//子控制命令 常量3
	private short triggleThreshold;		//阀值设定   需设定为下一次STA/LTA触发的阈值*100
	
	//getter and setter
//	public short getSubCommand() {
//		return subCommand;
//	}
//	public void setSubCommand(short subCommand) {
//		this.subCommand = subCommand;
//	}
	public short getTriggleThreshold() {
		return triggleThreshold;
	}
	public void setTriggleThreshold(short triggleThreshold) {
		this.triggleThreshold = triggleThreshold;
	}

}
