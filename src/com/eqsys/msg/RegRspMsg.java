package com.eqsys.msg;

/**
 * 2.注册结果响应包
 * 发送端：服务端
 * 标识符：RR
 * 
 */
public class RegRspMsg extends BaseMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int packetId;
	private short authenState;   //认证状态  0成功，-1失败
	private int lastPacketNo;    //上一次包序号  0xffffffff 不断电续传
	
	
	//getter and setter
	
	public short getAuthenState() {
		return authenState;
	}
	public int getPacketId() {
		return packetId;
	}
	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}
	public void setAuthenState(short authenState) {
		this.authenState = authenState;
	}
	public int getLastPacketNo() {
		return lastPacketNo;
	}
	public void setLastPacketNo(int lastPacketNo) {
		this.lastPacketNo = lastPacketNo;
	}
	
//	@Override
//	public String toString() {
//		
//		return "[包序号："+this.packetNo+"  "+
//				"类型："+this.msgType+"  "+
//				"台站ID："+this.stationId+"  "+
//				"认证状态："+this.authenState+"  "+
//				"上次包序号："+this.lastPacketNo+"]"
//				;
//	}
}
