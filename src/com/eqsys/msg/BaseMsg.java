package com.eqsys.msg;

import java.io.Serializable;

public class BaseMsg implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgType;  			//类型
	private String srvId;				//服务端ID 2bytes
	private String stId;			    //烈度仪ID  5bytes
	
	//getter and setter
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSrvId() {
		return srvId;
	}
	public void setSrvId(String srvId) {
		this.srvId = srvId;
	}
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	
	
	
	
	
}
