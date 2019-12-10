package com.zte.smartchat.entity;
/**
 * 传输到前台的员工模型
 * @author liyuxin
 *
 */
public class StaffToFore extends Staff {
	/**
	 * 连接前台时的类型   用来判断是员工说话，还是上线,下线
	 * 1：群聊文本消息  
	 * 2：建立连接消息
	 * 3：关闭连接消息
	 * 4.消息缓存消息
	 * 5.私聊文本消息
	 */
	private int messageType;

	
	
	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public StaffToFore(Integer id, String name, String headUrl, String power, String state) {
		super(id, name, headUrl, power, state);
	}

	public StaffToFore(Integer id, String name, String headUrl, String power, String state, int messageType) {
		super(id, name, headUrl, power, state);
		this.messageType = messageType;
	}
	

	public StaffToFore() {
		super();
	}

	
}
