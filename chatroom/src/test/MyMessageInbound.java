package xin.test;

import java.io.IOException;   
import java.nio.ByteBuffer;   
import java.nio.CharBuffer;
import java.util.Vector;

import org.apache.catalina.websocket.MessageInbound;   
import org.apache.catalina.websocket.WsOutbound;   

/**
 * 使用catalina.websocket下的api
 * 已过时
 * @author liyuxin
 *
 */
public class MyMessageInbound extends MessageInbound {
	
	//这里建一个LIST 用来存放MessageInbound的对象，每一次连接都放个对象进去，方便对连接进行管理
	private static Vector<MessageInbound> v = new Vector<MessageInbound>();
	private String ip = "";

	public MyMessageInbound(String ip){
		this.ip = ip;
	}

	@Override  
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {   
		// TODO Auto-generated method stub   
		System.out.println("onBinaryMessage:"+arg0);
	}   

	@Override  
    protected void onTextMessage(CharBuffer msg) throws IOException {
		System.out.println("[事件：onTextMessage][参数：status="+msg+"]");
        for(int i=0; i<v.size(); i++){
	        try {
	        	MessageInbound mib = (MessageInbound)v.get(i);
	        	CharBuffer buffer = CharBuffer.wrap(this.ip+"说："+msg);
	        	mib.getWsOutbound().writeTextMessage(buffer); 
	        } catch (IOException e) { 
	            System.out.println("当有用户连接时发消息异常");
	        }
        }
	}

	@Override  
	protected void onClose(int status) {
		System.out.print("[事件：onClose][参数：status="+status+"]");
		v.remove(this);
		System.out.println("[连接："+v.size()+"]");
        for(int i=0; i<v.size(); i++){
	        try {
	        	MessageInbound mib = (MessageInbound)v.get(i);
	        	CharBuffer buffer = CharBuffer.wrap("用户"+this.ip+"离开了聊天室");
	        	mib.getWsOutbound().writeTextMessage(buffer); 
	        } catch (IOException e) { 
	            System.out.println("用户离开发消息异常");
	        }
        }
	}   

	@Override  
	protected void onOpen(WsOutbound outbound) {
		
		System.out.print("[事件：onOpen][参数：outbound="+outbound+"]");
        v.add(this);
        System.out.println("[连接："+v.size()+"]");

        for(int i=0; i<v.size(); i++){
	        try {
	        	MessageInbound mib = (MessageInbound)v.get(i);
	        	CharBuffer buffer = CharBuffer.wrap("用户"+this.ip+"进入聊天室！当前连接数"+v.size());
	        	mib.getWsOutbound().writeTextMessage(buffer); 
	        } catch (IOException e) { 
	            System.out.println("当有用户连接时发消息异常");
	        }
        }
	}
	

}  
