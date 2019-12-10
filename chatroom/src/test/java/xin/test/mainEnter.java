package xin.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.util.Mybatisutil;
import com.zte.smartchat.util.PropertiesReader;



public class mainEnter {
	public static void main(String[] args) {
		SimpleDateFormat formatBuilder = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		String dateString = formatBuilder.format(new Date());
		System.out.println(dateString);
	}
	
	public void getProperties(){
		String[] names = {"maxCapacity"};
		PropertiesReader pReader = new PropertiesReader();
		
		try {
			HashMap<String, String> hm = pReader.getProperties("msgCache.properties", names);
			System.out.println(hm.get("maxCapacity"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
