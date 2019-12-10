package com.zte.smartchat.entity;

/**
 * 聊天消息的JSON对象
 * @author TXZ
 *
 */

public class MsgJson {
	/**
	 * 聊天目标的类型
	 * public：群聊消息；private：私聊消息
	 */
	private String targetType;	
	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 聊天目标的ID
	 */
	private int targetId;
	/**
	 * 消息所属类型 
	 * N：普通消息   I：图片消息
	 */
	private String type;		
	
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
