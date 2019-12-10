package per.xin.chatroom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

/**
 * 这是一个专用MD5加密的类
 * @author Yulong Hu
 *
 */
public final class MD5Util { 
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
	       '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
	protected static MessageDigest messagedigest = null;  
	static {  
	   try {  
	       messagedigest = MessageDigest.getInstance("MD5");  
	   } catch (NoSuchAlgorithmException e) {  
	   	//System.out.println("MD5FileUtil messagedigest初始化失败"+ e);
	   	LogManager.getLogger(MD5Util.class).log(Level.WARNING,"MD5FileUtil messagedigest初始化失败",e);
	   }  
	}  
		
	private MD5Util(){
		
	}
	/** 
    * md5加密(ITS) 
    * @param str 
    * @param charSet 
    * @return 
    */  
   public synchronized static String getMD5Str(String str,String charSet) { //md5加密  
	    MessageDigest messageDigest = null;    
	    try {    
	        messageDigest = MessageDigest.getInstance("MD5");    
	        messageDigest.reset();   
	        if(charSet==null){  
	            messageDigest.update(str.getBytes());  
	        }else{  
	            messageDigest.update(str.getBytes(charSet));    
	        }             
	    } catch (NoSuchAlgorithmException e) {    
	        //System.out.println("md5 error:"+e.getMessage()); 
	        LogManager.getLogger(MD5Util.class).log(Level.WARNING,"md5 error:",e);
	    } catch (UnsupportedEncodingException e) {    
	    	//System.out.println("md5 error:"+e.getMessage()); 
	    	LogManager.getLogger(MD5Util.class).log(Level.WARNING,"md5 error:",e);
	    }    
	      
	    byte[] byteArray = messageDigest.digest();    
	    StringBuffer md5StrBuff = new StringBuffer();    
	    for (int i = 0; i < byteArray.length; i++) {                
	        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)    
	            md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));    
	        else    
	            md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));    
	    }    
	    return md5StrBuff.toString();    
   }  
   
   
	
	public static String getFileMD5String(File file) throws IOException {  
	   FileInputStream in = new FileInputStream(file);  
	   FileChannel ch = in.getChannel();  
	   MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,  
	           file.length());  
	   messagedigest.update(byteBuffer);  
	   return bufferToHex(messagedigest.digest());  
	}  
	
	public static String getMD5String(String s) {  
	   return getMD5String(s.getBytes());  
	}  
	
	public static String getMD5String(byte[] bytes) {  
	   messagedigest.update(bytes);  
	   return bufferToHex(messagedigest.digest());  
	}  
	
	private static String bufferToHex(byte bytes[]) {  
	   return bufferToHex(bytes, 0, bytes.length);  
	}  
	
	private static String bufferToHex(byte bytes[], int m, int n) {  
	   StringBuffer stringbuffer = new StringBuffer(2 * n);  
	   int k = m + n;  
	   for (int l = m; l < k; l++) {  
	       appendHexPair(bytes[l], stringbuffer);  
	   }  
	   return stringbuffer.toString();  
	}  
	
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
	   char c0 = hexDigits[(bt & 0xf0) >> 4];  
	   char c1 = hexDigits[bt & 0xf];  
	   stringbuffer.append(c0);  
	   stringbuffer.append(c1);  
	}  
	
	public static boolean checkPassword(String password, String md5PwdStr) {  
	   String s = getMD5String(password);  
	   return s.equals(md5PwdStr);  
	}  
	
	
	public static String getMD5Byinput(InputStream is) throws NoSuchAlgorithmException, IOException {
	   StringBuffer md5 = new StringBuffer();
	   MessageDigest md = MessageDigest.getInstance("MD5");
	   byte[] dataBytes = new byte[1024];
	   
	   int nread = 0; 
	   while ((nread = is.read(dataBytes)) != -1) {
	     md.update(dataBytes, 0, nread);
	   }
	   byte[] mdbytes = md.digest();
	   
	   // convert the byte to hex format
	   for (int i = 0; i < mdbytes.length; i++) {
	     md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	   }
	   return md5.toString();
	 }
}
