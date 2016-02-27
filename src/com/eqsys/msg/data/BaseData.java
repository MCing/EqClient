package com.eqsys.msg.data;

import java.io.Serializable;

public class BaseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 包序号 */
	private int id;   

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
