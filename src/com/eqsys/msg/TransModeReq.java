package com.eqsys.msg;

import java.io.Serializable;

public class TransModeReq extends CommandReq{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private short subCommand;  			//子控制命令 
	private short subTransMode;         //传输模式 0 不改变传输模式 1 设定为连续传输模 2 设定为触发传输传波形 3 设定为触发传输不传波形
	
	
//	public short getSubCommand() {
//		return subCommand;
//	}
//	public void setSubCommand(short subCommand) {
//		this.subCommand = subCommand;
//	}
	public short getSubTransMode() {
		return subTransMode;
	}
	public void setSubTransMode(short subTransMode) {
		this.subTransMode = subTransMode;
	}
}
