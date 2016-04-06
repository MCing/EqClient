package com.eqsys.msg.data;

import java.io.Serializable;

public class WavefData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;      //包序号,此包序号与Header中的相同,这里是为了配合数据库使用
	private String qid = "D"; //质量标识符 1B  常量D
	private byte hrsv;   //保留字节
	private String localId; //位置标识符 2B
	private String channId; //通道标识符  3B
	private long startTime; //记录起始时间 绝对UTC 10byte ????
	private short samNum;  //样品数量
	private short samFactor; //采样因子
	private short samMul; //采样率乘数
	private byte actId; //活动标志
	private byte iocFlag; //输入输出和时钟标志
	private byte dqFlag; //数据质量标志
	private byte blockNum; //后面子块数目
	private int timeCorr; //时间校正值
	private short startOffs; //数据开始偏移量
	private short subBlockOffs; //第一个数据子块偏移
	
	//sub header
	private short hBlockType; //子块类型
	private short blockId; //下一子块字节号
	private byte codeFormat; //编码格式
	private byte order; //子序
	private byte dataLen; //记录数据长度
	private byte hblockrsv;    //保留字段
	
	//sub block
	private short blockType; //子块类型 
	private byte dim; //量纲
	private int sensFactor; //灵敏度因子
	private byte blockRsv;  //保留字段
	
	private byte[] dataBlock;    // 待定,不能直接用Blob,没有构造方法

	//getter and setter
	
	public String getQuality() {
		return qid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setQuality(String quality) {
		this.qid = quality;
	}

	public byte getHrsv() {
		return hrsv;
	}

	public void setHrsv(byte hrsv) {
		this.hrsv = hrsv;
	}

	public String getLocId() {
		return localId;
	}

	public void setLocId(String locId) {
		this.localId = locId;
	}

	public String getChannId() {
		return channId;
	}

	public void setChannId(String chanId) {
		this.channId = chanId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public short getSamNum() {
		return samNum;
	}

	public void setSamNum(short samNum) {
		this.samNum = samNum;
	}

	public short getSamFactor() {
		return samFactor;
	}

	public void setSamFactor(short factor) {
		this.samFactor = factor;
	}

	public short getSamMul() {
		return samMul;
	}

	public void setSamMul(short mulFactor) {
		this.samMul = mulFactor;
	}

	public byte getActFlag() {
		return actId;
	}

	public void setActFlag(byte actFlag) {
		this.actId = actFlag;
	}

	public byte getIocFlag() {
		return iocFlag;
	}

	public void setIocFlag(byte iocFlag) {
		this.iocFlag = iocFlag;
	}

	public byte getDqFlag() {
		return dqFlag;
	}

	public void setDqFlag(byte dqFlag) {
		this.dqFlag = dqFlag;
	}

	public byte getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(byte blockNum) {
		this.blockNum = blockNum;
	}

	public int getTimeCorr() {
		return timeCorr;
	}

	public void setTimeCorr(int timeCorr) {
		this.timeCorr = timeCorr;
	}

	public short getStartOffs() {
		return startOffs;
	}

	public void setStartOffs(short startOffs) {
		this.startOffs = startOffs;
	}

	public short getSubBlockOffs() {
		return subBlockOffs;
	}

	public void setSubBlockOffs(short subBlockOffs) {
		this.subBlockOffs = subBlockOffs;
	}

	public short gethBlockType() {
		return hBlockType;
	}

	public void sethBlockType(short hBlockType) {
		this.hBlockType = hBlockType;
	}

	public short getBlockId() {
		return blockId;
	}

	public void setBlockId(short blockId) {
		this.blockId = blockId;
	}

	public byte getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(byte codeFm) {
		this.codeFormat = codeFm;
	}

	public byte getOrder() {
		return order;
	}

	public void setOrder(byte order) {
		this.order = order;
	}

	public byte getDataLen() {
		return dataLen;
	}

	public void setDataLen(byte length) {
		this.dataLen = length;
	}

	public byte getHblockrsv() {
		return hblockrsv;
	}

	public void setHblockrsv(byte hblockrsv) {
		this.hblockrsv = hblockrsv;
	}

	public short getBlockType() {
		return blockType;
	}

	public void setBlockType(short blockType) {
		this.blockType = blockType;
	}

	public byte getDim() {
		return dim;
	}

	public void setDim(byte dim) {
		this.dim = dim;
	}

	public int getSensFactor() {
		return sensFactor;
	}

	public void setSensFactor(int sensFactor) {
		this.sensFactor = sensFactor;
	}

	public byte getBlockRsv() {
		return blockRsv;
	}

	public void setBlockRsv(byte blockRsv) {
		this.blockRsv = blockRsv;
	}

	public byte[] getDataBlock() {
		return dataBlock;
	}

	public void setDataBlock(byte[] data) {
		this.dataBlock = data;
	}
	

	
	
}
