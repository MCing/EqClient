package com.eqsys.msg;

/**
 * 7.控制命令应答包
 * 发送端：客户端
 * 标识符:CR
 *
 */
public class CtrlCmdRspMsg extends BaseCmdMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private short rspState;		  //应答状态  0 状态正常，即将响应  1 忙，稍后响应  2 网络不通  3 命令错误  4 无控制权  5 其他
	private String stateDetil;	  //详细说明状态的情况

	//getter and setter
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

	
//	@Override
//	public String toString() {
//		
//		return "[包序号："+this.packetNo+"  "+
//				"类型："+this.msgType+"  "+
//				"台站ID："+this.stationId+"  "+
//				"应答状态："+this.rspState+"  "+
//				"状态详情："+this.stateDetil+"]"
//				;
//	}
}
