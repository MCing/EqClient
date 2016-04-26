package com.eqcli.util;

import java.util.Random;


/**
 * 烈度仪配置
 *
 */
public class EqConfig {
	
	public static Random random = new Random();
	
	public static String stdId;// = String.format("%05d", new Random().nextInt(99999));				//烈度仪代号
	public  static float longitude;// = ((float)random.nextInt(300)*100000)/100000;				//经度 度＊100000
	public  static float latitude;// = ((float)random.nextInt(300)*100000)/100000;				//纬度 度＊100000
	public  static short altitude;// = (short) random.nextInt(200);				//高程 单位M
	public  static int sensitivity;// = random.nextInt(1000);			//灵敏度
	public  static short transMode;// = 1;			//传输模式  1:连续传输模式  2:为触发传输传波形  3:触发传输不传波形
	public  static short triggerThreshold ;//= (short) random.nextInt(200);		//触发阀值
	
}
