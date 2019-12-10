package com.zte.smartchat.util;

import java.io.IOException;  
import java.io.InputStream;  
import java.util.HashMap;
import java.util.Properties; 
import java.util.logging.Level;
/**
 * 这是一个专用读取配置文件的类
 * @author Yulong Hu
 *
 */
public final class PropertiesReader {
	
	
	public HashMap<String, String> getProperties(String uri, String[] names) throws IOException {  
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(uri);         
        Properties properties = new Properties();  
        
        try{  
            properties.load(inputStream);  
        }catch (IOException ioE){  
            ioE.printStackTrace();
            LogManager.getLogger(MD5Util.class).log(Level.WARNING,"PropertiesReader error:",ioE);
        }finally{  
            inputStream.close();  
        }  

        HashMap<String, String> hm = new HashMap<String, String>();
        
        for(int i=0; i<names.length; i++){
        	String value = properties.getProperty(names[i]);
        	if(null != value){
        		hm.put(names[i], value);
        	}
        }
        
        return hm;
    } 
}
