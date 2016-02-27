package ldy.test;

import java.lang.reflect.Field;

import com.eqcli.dao.CommDao;
import com.eqsys.msg.data.WavefData;

public class TestMain {

	public static void main(String[] args) {

		WavefData data = new WavefData();
		
//		Class clazz = data.getClass();
//		Class supClazz = clazz.getSuperclass();
//		
//		Field[] fields = clazz.getDeclaredFields();
//		Field[] supFields = supClazz.getDeclaredFields();
//		
//		for(Field f : supFields){
//			
//			
//			System.out.println(f.getName() + "     "+f.getType().getName());
//		}
//		for(Field f : fields){
//			
//			System.out.println(f.getName()+ "     "+f.getType().getSimpleName());
//		}
		CommDao.save(data);
		

	}

}
