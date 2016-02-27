package com.eqsys.msg;

/**
 * 3.传输模式控制包
 * 发送端：服务端
 * 标识符：CC
 *
 */
public class TransModeMsg extends BaseCmdMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private short subCommand;  			//子控制命令  常量1
	private short subTransMode;         //传输模式 0 不改变传输模式 1 设定为连续传输模 2 设定为触发传输传波形 3 设定为触发传输不传波形
	
	//getter and setter
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
	
//	@Override
//	public String toString() {
//		
//		return "[包序号："+this.packetNo+"  "+
//				"类型："+this.msgType+"  "+
//				"台站ID："+this.stationId+"  "+
//				"传输模式："+this.subTransMode+"  "+
//				"子命令："+this.subCommand+"]"
//				;
//	}
}
