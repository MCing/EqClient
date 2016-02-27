package com.eqsys.msg;

/**
 * 1.注册信息包格式
 * 发送端：客户端
 * 标识符：RE
 */
public class RegMsg extends BaseMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String authenCode;			//认证密码 
	private short ctrlAuthority;		//控制权限  0:可控制  -1：不可控制
	private int longitude;				//经度 度＊100000
	private int latitude;				//纬度 度＊100000
	private short altitude;				//高程 单位M
	private int sensitivity;			//灵敏度
	private short transMode;			//传输模式 1  设定为连续传输模式 2  设定为触发传输传波形 3  设定为触发传输不传波形
	private short triggerThreshold;		//触发阀值
	
	//getter and setter
	
	
	public short getTriggerThreshold() {
		return triggerThreshold;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTriggerThreshold(short triggerThreshold) {
		this.triggerThreshold = triggerThreshold;
	}
	public short getCtrlAuthority() {
		return ctrlAuthority;
	}
	public void setCtrlAuthority(short ctrlAuthority) {
		this.ctrlAuthority = ctrlAuthority;
	}
	public String getAuthenCode() {
		return authenCode;
	}
	public void setAuthenCode(String authenCode) {
		this.authenCode = authenCode;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public short getAltitude() {
		return altitude;
	}
	public void setAltitude(short altitude) {
		this.altitude = altitude;
	}
	public int getSensitivity() {
		return sensitivity;
	}
	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
	public short getTransMode() {
		return transMode;
	}
	public void setTransMode(short transMode) {
		this.transMode = transMode;
	}

	
//	@Override
//	public String toString() {
//
//		return "[包序号:"+this.packetId+"  "+
//				"类型:"+this.msgType+"  "+
//				"台站ID:"+this.stationId+"  "+
//				"认证密码:"+this.authenCode+"  "+
//				"控制权限:"+this.ctrlAuthority+"  "+
//				"经度:"+this.longitude+"  "+
//				"纬度:"+this.latitude+"  "+
//				"高程:"+this.altitude+"  "+
//				"灵敏度:"+this.sensitivity+"  "+
//				"传输模式:"+this.transMode+"  "+
//				"触发阀值:"+this.triggerThreshold+"]";
//	}
	
}
