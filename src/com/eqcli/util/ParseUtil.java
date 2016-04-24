package com.eqcli.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.eqcli.application.EqClient;

public class ParseUtil {

	
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
