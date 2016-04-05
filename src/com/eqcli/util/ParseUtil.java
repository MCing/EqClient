package com.eqcli.util;

public class ParseUtil {

	
	public static String parseTransMode(short mode){
		
		switch(mode){
		case 2:
			return "触发传输传波形";
		case 3:
			return "触发传输不传波形";
		case 1:
		default:
			return "连续波形传输"; 
		}
		
	}
	
}
