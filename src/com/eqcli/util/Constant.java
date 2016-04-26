package com.eqcli.util;

public class Constant {
	
	//更新UI类型码
	public static final short UICODE_STATE = 1;		//与台网连接状态
	public static final short UICODE_MODE = 2;		//传输模式
	public static final short UICODE_THREHOLD = 3;	//触发阈值
	public static final short UICODE_DBSTATE = 4;	//数据库连接状态
	public static final short UICODE_DATACREATOR = 5;	//模拟数据发生器状态
	
	//三种传输模式
	public static final short MODE_IDLE = -1;
	public static final short MODE_CONTINUOUS = 1;
	public static final short MODE_TRG_WAVE = 2;
	public static final short MODE_TRG_NWAV = 3;
	
	/* 传输模式  */
	public static final String TRANSMODE[] = {null, "连续波形传输", "触发传波形", "触发不传波形"};
	
	

}
