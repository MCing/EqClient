package com.eqsys.msg;

public class BaseCmdMsg extends BaseMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int packetId;
	private short subCommand;	  //子控制命令   回应对应的命令
	
	//getter and setter
	public int getPacketId() {
		return packetId;
	}
	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}
	public short getSubCommand() {
		return subCommand;
	}
	public void setSubCommand(short subCommand) {
		this.subCommand = subCommand;
	}
	
	
}
