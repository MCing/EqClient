package com.eqsys.msg;

public class MsgConstant {

	/* 消息包标识 */
	public static final String TYPE_RE = "RE";
	public static final String TYPE_CR = "CR";
	public static final String TYPE_WC = "WC";
	public static final String TYPE_WT = "WT";
	public static final String TYPE_WS = "WS";
	public static final String TYPE_SI = "SI";
	public static final String TYPE_TI = "TI";
	public static final String TYPE_RR = "RR";
	public static final String TYPE_CC = "CC";
	public static final String TYPE_HB = "HB";
	public static final String TYPE_HBR = "HR";
	
	/* 注册状态 */
	public static final short REG_SUCCESS = 0;
	public static final short REG_FAILURE = -1;
	
	/* 子命令 */
	public static final short CMD_TRANSMODE = 1;
	public static final short CMD_PERIODDATA = 2;
	public static final short CMD_TRGTHRESHOLD = 3;
	public static final short CMD_TRGPRIOD = 4;
}
