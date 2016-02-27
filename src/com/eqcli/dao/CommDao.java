package com.eqcli.dao;

import java.lang.reflect.Field;

public class CommDao {
	
	/** insert data */
	public static boolean save(Object obj){
		//insert into tablename(field...) values(?...);
		StringBuilder sb = new StringBuilder();
		StringBuilder tmpSb = new StringBuilder();
		
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		sb.append("insert into "+clazz.getSimpleName() + "(");
		tmpSb.append("values(");
		int fieldLen = fields.length;
		for(int i = 0; i < fieldLen; i++){
			
			if(i == fieldLen - 1){  //最后一个字段
				sb.append(fields[i].getName()+")");
				tmpSb.append("?);");
			}else{
				sb.append(fields[i].getName()+",");
				tmpSb.append("?,");
			}
		}
		
		sb.append(tmpSb);
		System.out.println(sb.toString());
		
		return false;
	}
}
