package com.eqcli.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.eqcli.application.EqClient;
import com.eqsys.msg.MsgConstant;

public class ParseUtil {
	
	/** 传输数据类型转换 */
	public static String parseDataType(String type){
		
		String typeStr = null;
		if(type.equals(MsgConstant.TYPE_WC)){    //连续波形数据
			typeStr = "连续波形数据";
		}else if(type.equals(MsgConstant.TYPE_WT)){	  //触发波形数据
			typeStr = "触发波形数据";
		}else if(type.equals(MsgConstant.TYPE_WS)){    //时间段波形数据
			typeStr = "时间段波形数据";
		}else if(type.equals(MsgConstant.TYPE_TI)){    //触发信息
			typeStr = "触发信息数据";
		}else if(type.equals(MsgConstant.TYPE_SI)){    //状态信息
			typeStr = "状态信息数据";
		}
		
		return typeStr;
	}

	
	/** 根据传输模式id 获取模式字符串 */
	public static String parseTransMode(short mode) {

		return Constant.TRANSMODE[mode];
	}
	
	/**
	 * 为了解决打包成jar包后找不到fxml文件的问题
	 * @param path  fxml的绝对路径(这里的绝对路径是相对于工程的)
	 * @return
	 */
	public static URL getFXMLURL(String path) {

		URL url = null;
		url = ParseUtil.class.getResource(path);
		try {
			//打包成jar包后不能通过getResource得到类路径
			//所以,jar包中的文件用url表示方法:  jar:url!{entity}
			//如 :     jar:file:/c:/a/b.jar!/com/test/a.txt
			if (url == null) {
				URL jarUrl = ParseUtil.class.getProtectionDomain()
						.getCodeSource().getLocation();
				url = new URL("jar:" + jarUrl + "!" + path);
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return url;
	}
	
}
