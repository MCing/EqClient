package com.eqsys.msg.data;

import java.io.Serializable;

/**
 * 波形数据 [1002] 子块
 *
 */
public class WavefSubBlock implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private short subBlockType; //子块类型 
	private byte dimension; //量纲
	private int sensFactor; //灵敏度因子
	private byte reserve;  //保留字段
	
	//getter and setter
	public short getSubBlockType() {
		return subBlockType;
	}
	public void setSubBlockType(short subBlockType) {
		this.subBlockType = subBlockType;
	}
	public byte getDimension() {
		return dimension;
	}
	public void setDimension(byte dimension) {
		this.dimension = dimension;
	}
	public int getSensFactor() {
		return sensFactor;
	}
	public void setSensFactor(int sensFactor) {
		this.sensFactor = sensFactor;
	}
	public byte getReserve() {
		return reserve;
	}
	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}
	
}
