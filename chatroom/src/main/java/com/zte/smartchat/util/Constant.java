package com.zte.smartchat.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import com.zte.smartchat.action.WebSocketTest;

/**
 * 静态常量 
 * 与StaffToFore中的messageType相对应
 * @author TXZ
 *
 */
public class Constant {
	
	
	/**信号消息*/
	public static final int MESSAGE_SIGNAL = 0;
	/**普通消息 文本或图片*/
	public static final int MESSAGE_NORMAL = 1;	
	/**建立连接消息*/
	public static final int MESSAGE_OPEN = 2; 	//建立连接消息
	/**关闭连接消息*/
	public static final int MESSAGE_CLOSE = 3;	//关闭连接消息	
	/**消息缓存消息*/
	public static final int MESSAGE_HISTORY = 4;	//消息记录
	/**私聊文本消息*/
	public static final int MESSAGE_PRIVATE = 5;	//私聊文本消息
	
	/**服务路径*/
	public static final String SERVER_ADDRESS = "http://localhost:8080/SmartChat/";
	

	public static int MAX_CAPACITY;
	public static String SHAREFLRE_LOCATION;
	public static String IMG_PATH;
	
	
	static{
		String[] names = {"maxCapacity","shareFileLocation","imagePath"};
		PropertiesReader pReader = new PropertiesReader();
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			hm = pReader.getProperties("smartChat.properties", names);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"从smartChat.properties中读取maxCapacity"
					+ "、shareFileLocation失败，请检查该配置文件");
			MAX_CAPACITY = 10;
		}
		
		if(null != hm.get("maxCapacity")){
			try{
				MAX_CAPACITY = Integer.parseInt(hm.get("maxCapacity"));
			}catch(NumberFormatException e){
				LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"请确保msgCache.properties中的maxCapacity的值为整数");
				MAX_CAPACITY = 10;
			}
		}else{
			MAX_CAPACITY = 10;
		}
		
		if(null != hm.get("shareFileLocation")){
			try{
				SHAREFLRE_LOCATION = hm.get("shareFileLocation");
			}catch(NumberFormatException e){
				LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"请确保msgCache.properties中的shareFileLocation的值的正确性");
				SHAREFLRE_LOCATION = "D:/JAVA/apache-tomcat-7.0.67/webapps/SmartChat/files/";
			}
		}else{
			SHAREFLRE_LOCATION = "D:/JAVA/apache-tomcat-7.0.67/webapps/SmartChat/files/";
		}
		
		if(null != hm.get("imagePath")){
			try{
				IMG_PATH = hm.get("imagePath");
			}catch(NumberFormatException e){
				LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"请确保msgCache.properties中的imagePath的值的正确性");
				IMG_PATH = "D:/JAVA/apache-tomcat-7.0.67/webapps/SmartChat";
			}
		}else{
			IMG_PATH = "D:/JAVA/apache-tomcat-7.0.67/webapps/SmartChat";
		}
		
	}
}
