package com.eqsys.msg.data;

import java.io.Serializable;

/**
 * 波形数据包 固定头部
 * 备注:标识符在WavefDataMsg中定义;包序号在BaseData中
 *
 */
public class WavefHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private char qualityIdenty = 'D'; //质量标识符 1B  常量D
	private byte reserve;   //保留字节
	private String localtionId; //位置标识符 2B
	private String channelId; //通道标识符  3B
	private long startTime; //记录起始时间 绝对UTC 10byte ????
	private short sampleNum;  //样品数量
	private short sampleFactor; //采样因子
	private short sampleMul; //采样率乘数
	private byte activitySign; //活动标志
	private byte ioClockSing; //输入输出和时钟标志
	private byte dataQualitySign; //数据质量标志
	private byte subBlockNum; //后面子块数目
	private long timeCorr; //时间校正值
	private short dataStartOffset; //数据开始偏移量
	private short firstBlockOffset; //第一个数据子块偏移
	
	//getter and setter
	public byte getReserve() {
		return reserve;
	}
	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}
	public char getQualityIdenty() {
		return qualityIdenty;
	}
	public void setQualityIdenty(char qualityIdenty) {
		this.qualityIdenty = qualityIdenty;
	}
	public String getLocaltionId() {
		return localtionId;
	}
	public void setLocaltionId(String localtionId) {
		this.localtionId = localtionId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public short getSampleNum() {
		return sampleNum;
	}
	public void setSampleNum(short sampleNum) {
		this.sampleNum = sampleNum;
	}
	public short getSampleFactor() {
		return sampleFactor;
	}
	public void setSampleFactor(short sampleFactor) {
		this.sampleFactor = sampleFactor;
	}
	public short getSampleMul() {
		return sampleMul;
	}
	public void setSampleMul(short sampleMul) {
		this.sampleMul = sampleMul;
	}
	public byte getActivitySign() {
		return activitySign;
	}
	public void setActivitySign(byte activitySign) {
		this.activitySign = activitySign;
	}
	public byte getIoClockSing() {
		return ioClockSing;
	}
	public void setIoClockSing(byte ioClockSing) {
		this.ioClockSing = ioClockSing;
	}
	public byte getDataQualitySign() {
		return dataQualitySign;
	}
	public void setDataQualitySign(byte dataQualitySign) {
		this.dataQualitySign = dataQualitySign;
	}
	public byte getSubBlockNum() {
		return subBlockNum;
	}
	public void setSubBlockNum(byte subBlockNum) {
		this.subBlockNum = subBlockNum;
	}
	public long getTimeCorr() {
		return timeCorr;
	}
	public void setTimeCorr(long timeCorr) {
		this.timeCorr = timeCorr;
	}
	public short getDataStartOffset() {
		return dataStartOffset;
	}
	public void setDataStartOffset(short dataStartOffset) {
		this.dataStartOffset = dataStartOffset;
	}
	public short getFirstBlockOffset() {
		return firstBlockOffset;
	}
	public void setFirstBlockOffset(short firstBlockOffset) {
		this.firstBlockOffset = firstBlockOffset;
	}
	
	

}
