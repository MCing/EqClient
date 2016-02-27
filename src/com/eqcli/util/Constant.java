package com.eqcli.util;

import java.util.Random;

public class Constant {
	
	//站台Id
	public static String stationId = "00"+new Random().nextInt(1000);
	
	//服务器 id
	public static String serverId = "TT";
	
	//注册认证码
	public static String authorcode = "PASSWORD";
	
	//三种传输模式
	public static final short MODE_IDLE = 0;
	public static final short MODE_CONTINUOUS = 1;
	public static final short MODE_TRG_WAVE = 2;
	public static final short MODE_TRG_NWAV = 3;
	
	

}
