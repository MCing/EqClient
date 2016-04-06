package com.eqsys.msg;

import java.io.Serializable;

public class RegResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private short authenState;   //认证状态  0成功，-1失败
	private int lastPacketNo;    //上一次包序号  0xffffffff 不断电续传
	public short getAuthenState() {
		return authenState;
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
}
