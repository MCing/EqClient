package com.eqsys.msg;

import java.io.Serializable;

public class CommandResp implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private short rspState;		  //应答状态  0 状态正常，即将响应  1 忙，稍后响应  2 网络不通  3 命令错误  4 无控制权  5 其他
	private String stateDetil;	  //详细说明状态的情况
	private short subCommand;	  //子控制命令   回应对应的命令
	public short getRspState() {
		return rspState;
	}
	public void setRspState(short rspState) {
		this.rspState = rspState;
	}
	public String getStateDetil() {
		return stateDetil;
	}
	public void setStateDetil(String stateDetil) {
		this.stateDetil = stateDetil;
	}
	public short getSubCommand() {
		return subCommand;
	}
	public void setSubCommand(short subCommand) {
		this.subCommand = subCommand;
	}
}
