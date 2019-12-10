package per.xin.chatroom.util;



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


	public static int MAX_CAPACITY = 10;

	
	

}
