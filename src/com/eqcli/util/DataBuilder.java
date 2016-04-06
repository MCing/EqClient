package com.eqcli.util;

import java.util.Random;

import com.eqcli.application.EqClient;
import com.eqsys.msg.CommandResp;
import com.eqsys.msg.EqMessage;
import com.eqsys.msg.Header;
import com.eqsys.msg.MsgConstant;
import com.eqsys.msg.RegReq;
import com.eqsys.msg.data.StatusData;
import com.eqsys.msg.data.TrgData;
import com.eqsys.msg.data.WavefData;

public class DataBuilder {

	public static Random random = new Random();

	/** 构造 注册包 */
	public static EqMessage buildRegMsg() {

		EqMessage msg = new EqMessage();
		Header header = new Header();
		header.setMsgType(MsgConstant.TYPE_RE);
		header.setServerId(EqConfig.defaultSrv);
		header.setStationId(EqConfig.stdId);
		header.setPid(random.nextInt(100)); // 包序号随机产生
		RegReq regMsg = new RegReq();
		regMsg.setAuthenCode(EqConfig.authenCode);
		regMsg.setAltitude(EqConfig.altitude);
		regMsg.setLatitude(EqConfig.latitude);
		regMsg.setLongitude(EqConfig.longitude);
		regMsg.setSensitivity(EqConfig.sensitivity);
		regMsg.setTransMode(EqClient.transMode);
		regMsg.setTriggerThreshold(EqConfig.triggerThreshold);
		regMsg.setCtrlAuthority((short) 0);

		msg.setHeader(header);
		msg.setBody(regMsg);

		return msg;

	}

	/** 控制应答信息 */
	public static EqMessage buildCtrlRspMsg(int id, short state, String detil, short cmd) {

		EqMessage msg = new EqMessage();
		Header header = new Header();
		header.setMsgType(MsgConstant.TYPE_CR);
		header.setServerId(EqConfig.defaultSrv);
		header.setStationId(EqConfig.stdId);
		header.setPid(id);
		CommandResp ccRMsg = new CommandResp();
		ccRMsg.setRspState(state);
		ccRMsg.setStateDetil(detil);
		ccRMsg.setSubCommand(cmd);

		msg.setHeader(header);
		msg.setBody(ccRMsg);
		return msg;
	}

	/** 构造 触发信息参数数据 */
	public static TrgData buildTrgData() {

		TrgData data = new TrgData();

		data.setEwToPga(random.nextInt(2000));
		data.setEwToPgd(random.nextInt(2000));
		data.setEwToPgv(random.nextInt(2000));
		data.setEwToPsa03(random.nextInt(2000));
		data.setEwToPsa10(random.nextInt(2000));
		data.setEwToPsa10(random.nextInt(2000));
		data.setEwToPsa30(random.nextInt(2000));
		data.setInitMotionDir((short) random.nextInt(2000));
		data.setIntensity((short) random.nextInt(2000));
		data.setNsToPga(random.nextInt(2000));
		data.setNsToPgd(random.nextInt(2000));
		data.setNsToPgv(random.nextInt(2000));
		data.setNsToPsa03(random.nextInt(2000));
		data.setNsToPsa10(random.nextInt(2000));
		data.setNsToPsa30(random.nextInt(2000));
		data.setRelTimeSec((short) random.nextInt(2000));
		data.setStaAndltaValue(random.nextInt(2000));
		data.setStartTimeMs((short) random.nextInt(2000));
		data.setStartTimeSec(UTCTimeUtil.getUTCTimeLong());
		data.setUdToPga(random.nextInt(2000));
		data.setUdToPgd(random.nextInt(2000));
		data.setUdToPgv(random.nextInt(2000));
		data.setUdToPsa03(random.nextInt(2000));
		data.setUdToPsa10(random.nextInt(2000));
		data.setUdToPsa30(random.nextInt(2000));

		return data;
	}

	/** 构造状态信息数据 */
	public static StatusData buildStatusData(int id) {

		StatusData data = new StatusData();
		int[] ew = new int[10];
		int[] ns = new int[10];
		int[] ud = new int[10];
		for (int i = 0; i < 10; i++) {
			ew[i] = random.nextInt(1000);
			ns[i] = random.nextInt(1000);
			ud[i] = random.nextInt(1000);
		}
		data.setId(id);
		data.setDur(random.nextInt(100));
		;
		data.setStartTime(UTCTimeUtil.getUTCTimeLong());
		data.setEwPeakValue(ew);
		data.setNsPeakValue(ns);
		data.setUdPeakValue(ud);
		return data;
	}

	/** 构造状态信息消息包 */
	public static EqMessage buildStatusDataMsg(int id) {

		EqMessage msg = new EqMessage();
		Header header = new Header();
		header.setMsgType(MsgConstant.TYPE_SI);
		header.setPid(id);
		header.setServerId(EqConfig.defaultSrv);
		header.setStationId(EqConfig.stdId);

		msg.setHeader(header);
		msg.setBody(buildStatusData(id));
		return msg;
	}

	/** 构造波形数据包 */
	public static EqMessage buildWavefDataMsg(String type, WavefData data) {

		EqMessage msg = new EqMessage();

		Header header = new Header();
		header.setMsgType(type);
		header.setServerId(EqConfig.defaultSrv);
		header.setStationId(EqConfig.stdId);
		header.setPid(data.getId());
		msg.setHeader(header);
		
		msg.setBody(data);
		msg.setHeader(header);

		return msg;
	}

	/** 构造波形数据 */
	public static WavefData buildWavefData(int id) {
		WavefData data = new WavefData();
		// part of header
		data.setId(id);
		data.setLocId("00"); // 2Bytes
		data.setChannId("11"); // 2Bytes
		data.setStartTime(UTCTimeUtil.getUTCTimeLong()); //
		data.setSamNum((short) random.nextInt(Short.MAX_VALUE));
		data.setSamFactor((short) random.nextInt(100));
		data.setSamMul((short) random.nextInt(100));
		data.setActFlag((byte) random.nextInt(127));
		data.setIocFlag((byte) random.nextInt(127));
		data.setDqFlag((byte) random.nextInt(127));
		data.setBlockNum((byte) random.nextInt(127));
		data.setTimeCorr(random.nextInt());
		data.setStartOffs((short) random.nextInt(Short.MAX_VALUE));
		data.setSubBlockOffs((short) random.nextInt(Short.MAX_VALUE));
		// sub block header
		data.setOrder((byte) random.nextInt(127));
		data.setCodeFormat((byte) random.nextInt(127));
		data.setDataLen((byte) random.nextInt(127));
		data.setBlockId((short) random.nextInt(100));
		data.sethBlockType((short) random.nextInt(100));
		// sub block
		data.setDim((byte) random.nextInt(127));
		data.setSensFactor(random.nextInt(1000));
		data.setBlockType((short) random.nextInt(100));
		// data
		data.setDataBlock(new byte[192]);
		
		return data;
	}

	/** 构造 触发信息消息包 */
	public static EqMessage buildTriggleMsg(TrgData triggerData) {

		EqMessage msg = new EqMessage();
		Header header = new Header();
		header.setMsgType(MsgConstant.TYPE_TI);
		header.setPid(triggerData.getId());
		header.setServerId(EqConfig.defaultSrv);
		header.setStationId(EqConfig.stdId);

		msg.setHeader(header);
		msg.setBody(triggerData);
		return msg;
	}

}
