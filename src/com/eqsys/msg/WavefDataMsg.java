package com.eqsys.msg;

import com.eqcli.util.UTCTimeUtil;
import com.eqsys.msg.data.WavefData;

/**
 * 波形数据包消息
 *
 */
public class WavefDataMsg extends BaseMsg{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WavefData wavefData;
	
	//getter and setter
	public WavefData getWavefData() {
		return wavefData;
	}
	public void setWavefData(WavefData wavefData) {
		this.wavefData = wavefData;
	}
	
	@Override
	public String toString() {
		//tostring for packetid and startime
		return "["+getWavefData().getId()+","+UTCTimeUtil.parseUTCTime2Str(getWavefData().getStartTime())+"]";
	}
	
	
	
	
}
