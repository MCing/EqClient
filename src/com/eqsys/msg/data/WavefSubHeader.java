package com.eqsys.msg.data;

import java.io.Serializable;

/**
 * [1000]子块头段
 *
 */
public class WavefSubHeader implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private short subBlockType; //子块类型
	private short nextSubBlockType; //下一子块字节号
	private byte codeFormat; //编码格式
	private byte byteOrder; //子序
	private byte dataLength; //记录数据长度
	private byte reserve;    //保留字段
	
	//getter and setter
	public short getSubBlockType() {
		return subBlockType;
	}
	public void setSubBlockType(short subBlockType) {
		this.subBlockType = subBlockType;
	}
	public short getNextSubBlockType() {
		return nextSubBlockType;
	}
	public void setNextSubBlockType(short nextSubBlockType) {
		this.nextSubBlockType = nextSubBlockType;
	}
	public byte getCodeFormat() {
		return codeFormat;
	}
	public void setCodeFormat(byte codeFormat) {
		this.codeFormat = codeFormat;
	}
	public byte getByteOrder() {
		return byteOrder;
	}
	public void setByteOrder(byte byteOrder) {
		this.byteOrder = byteOrder;
	}
	public byte getDataLength() {
		return dataLength;
	}
	public void setDataLength(byte dataLength) {
		this.dataLength = dataLength;
	}
	public byte getReserve() {
		return reserve;
	}
	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}
	
	
}
