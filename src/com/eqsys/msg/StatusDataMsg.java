package com.eqsys.msg;

import com.eqsys.msg.data.StatusData;

/**
 * 8.状态信息包
 * 发送端：客户端
 * 标识符：SI
 *
 *	未完成,UD 峰值 数据长度问题:
 */
public class StatusDataMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StatusData statusData;

	public StatusData getStatusData() {
		return statusData;
	}

	public void setStatusData(StatusData statusData) {
		this.statusData = statusData;
	}
	
	

}
