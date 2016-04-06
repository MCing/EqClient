package com.eqsys.msg.data;

import java.io.Serializable;

/**
 * 触发信息数据
 *
 */
public class TrgData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;      //包序号,此包序号与Header中的相同,这里是为了配合数据库使用
	private long startTimeSec; 		//初动时间整秒   触发时间整秒值（绝对UTC时间）
	private short startTimeMs;		//初动时间毫秒 触发时间整毫秒值（绝对UTC时间）
	private short  relTimeSec;			//相对时间	单位秒，与初动时间的相对时间
	private int staAndltaValue;			//触发时计算得到的STA/LTA值
	private short initMotionDir;		//初动方向	 	1:向上 -1:向下 0:不清晰
	private int udToPga;				//单位为mm/s/s,频带范围0.1-10Hz
	private int udToPgv;				//单位为mm/s,频带范围0.1-10Hz
	private int udToPgd;				//单位为um,频带范围0.1-10Hz
	private int ewToPga;
	private int ewToPgv;
	private int ewToPgd;
	private int nsToPga;
	private int nsToPgv;
	private int nsToPgd;
	private short intensity;			//烈度值  单位 度*10
	private int udToPsa03;				//单自由度系统拟加速度反应，单位mm／s/s,阻尼比为5%，周期为0.3秒
	private int udToPsa10;				//单自由度系统拟加速度反应，单位mm／s/s,阻尼比为5%，周期为1秒
	private int udToPsa30;				//单自由度系统拟加速度反应，单位mm／s/s,阻尼比为5%，周期为3秒
	private int ewToPsa03;
	private int ewToPsa10;
	private int ewToPsa30;
	private int nsToPsa03;
	private int nsToPsa10;
	private int nsToPsa30;

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getStartTimeSec() {
		return startTimeSec;
	}
	public void setStartTimeSec(long startTimeSec) {
		this.startTimeSec = startTimeSec;
	}
	public short getStartTimeMs() {
		return startTimeMs;
	}
	public void setStartTimeMs(short startTimeMs) {
		this.startTimeMs = startTimeMs;
	}
	public short getRelTimeSec() {
		return relTimeSec;
	}
	public void setRelTimeSec(short relTimeSec) {
		this.relTimeSec = relTimeSec;
	}
	public int getStaAndltaValue() {
		return staAndltaValue;
	}
	public void setStaAndltaValue(int staAndltaValue) {
		this.staAndltaValue = staAndltaValue;
	}
	public short getInitMotionDir() {
		return initMotionDir;
	}
	public void setInitMotionDir(short initMotionDir) {
		this.initMotionDir = initMotionDir;
	}
	public int getUdToPga() {
		return udToPga;
	}
	public void setUdToPga(int udToPga) {
		this.udToPga = udToPga;
	}
	public int getUdToPgv() {
		return udToPgv;
	}
	public void setUdToPgv(int udToPgv) {
		this.udToPgv = udToPgv;
	}
	public int getUdToPgd() {
		return udToPgd;
	}
	public void setUdToPgd(int udToPgd) {
		this.udToPgd = udToPgd;
	}
	public int getEwToPga() {
		return ewToPga;
	}
	public void setEwToPga(int ewToPga) {
		this.ewToPga = ewToPga;
	}
	public int getEwToPgv() {
		return ewToPgv;
	}
	public void setEwToPgv(int ewToPgv) {
		this.ewToPgv = ewToPgv;
	}
	public int getEwToPgd() {
		return ewToPgd;
	}
	public void setEwToPgd(int ewToPgd) {
		this.ewToPgd = ewToPgd;
	}
	public int getNsToPga() {
		return nsToPga;
	}
	public void setNsToPga(int nsToPga) {
		this.nsToPga = nsToPga;
	}
	public int getNsToPgv() {
		return nsToPgv;
	}
	public void setNsToPgv(int nsToPgv) {
		this.nsToPgv = nsToPgv;
	}
	public int getNsToPgd() {
		return nsToPgd;
	}
	public void setNsToPgd(int nsToPgd) {
		this.nsToPgd = nsToPgd;
	}
	public short getIntensity() {
		return intensity;
	}
	public void setIntensity(short intensity) {
		this.intensity = intensity;
	}
	public int getUdToPsa03() {
		return udToPsa03;
	}
	public void setUdToPsa03(int udToPsa03) {
		this.udToPsa03 = udToPsa03;
	}
	public int getUdToPsa10() {
		return udToPsa10;
	}
	public void setUdToPsa10(int udToPsa10) {
		this.udToPsa10 = udToPsa10;
	}
	public int getUdToPsa30() {
		return udToPsa30;
	}
	public void setUdToPsa30(int udToPsa30) {
		this.udToPsa30 = udToPsa30;
	}
	public int getEwToPsa03() {
		return ewToPsa03;
	}
	public void setEwToPsa03(int ewToPsa03) {
		this.ewToPsa03 = ewToPsa03;
	}
	public int getEwToPsa10() {
		return ewToPsa10;
	}
	public void setEwToPsa10(int ewToPsa10) {
		this.ewToPsa10 = ewToPsa10;
	}
	public int getEwToPsa30() {
		return ewToPsa30;
	}
	public void setEwToPsa30(int ewToPsa30) {
		this.ewToPsa30 = ewToPsa30;
	}
	public int getNsToPsa03() {
		return nsToPsa03;
	}
	public void setNsToPsa03(int nsToPsa03) {
		this.nsToPsa03 = nsToPsa03;
	}
	public int getNsToPsa10() {
		return nsToPsa10;
	}
	public void setNsToPsa10(int nsToPsa10) {
		this.nsToPsa10 = nsToPsa10;
	}
	public int getNsToPsa30() {
		return nsToPsa30;
	}
	public void setNsToPsa30(int nsToPsa30) {
		this.nsToPsa30 = nsToPsa30;
	}
	
}
