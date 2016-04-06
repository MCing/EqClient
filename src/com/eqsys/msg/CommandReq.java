package com.eqsys.msg;

import java.io.Serializable;

/**
 * 控制命令消息包应继承这个类,方便客户端辨识子命令
 *
 */
public class CommandReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private short subCommand;  			//子控制命令 
	public short getSubCommand() {
		return subCommand;
	}
	public void setSubCommand(short subCommand) {
		this.subCommand = subCommand;
	}
}
