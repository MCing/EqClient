package com.eqsys.msg;

/**
 * 4.时间段申请数据包
 * 发送端：服务端
 * 标识符：CC
 */
public class PeriodReqMsg extends BaseCmdMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private short subCommand;    	//子控制命令  常量2
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
	
//	@Override
//	public String toString() {
//		
//		return "[包序号："+this.packetNo+"  "+
//				"类型："+this.msgType+"  "+
//				"台站ID："+this.stationId+"  "+
//				"子命令："+this.subCommand+"  "+
//				"时间码："+this.timeCode+"]\r\n"
//				;
//	}
}
