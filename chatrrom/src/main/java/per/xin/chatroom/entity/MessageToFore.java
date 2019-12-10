package per.xin.chatroom.entity;

import java.util.Date;

/**
 * 聊天记录  （继承Message）
 * @author TXZ
 *
 */
public class MessageToFore extends Message {
	/**
	 * 发信人姓名
	 */
	private String staffName;
	/**
	 * 消息类型
	 */
	private int messageType;	
	/**
	 * 发信人用户头像链接
	 */
	private String headUrl;
	
	
	public MessageToFore() {
		super();
	}

	public MessageToFore(Integer id, String content, Integer staffId, Date createdDt, String type, Integer targetId, String extra, String headUrl, String staffName,int messageType){
		super(id, content, staffId, createdDt, type, targetId, extra);
		this.staffName = staffName;
		this.messageType = messageType;
		this.headUrl = headUrl;
	}
	
	public MessageToFore(String headUrl, String staffName,int messageType, String content, Integer staffId, Date createdDt, String type, Integer targetId) {
		/*调用父类带参数的构造函数*/
		super(content, staffId, createdDt, type, targetId);
		this.staffName = staffName;
		this.messageType = messageType;
		this.headUrl = headUrl;
	}
	
	@Override
	public String toString() {
		return "PrivateMsgToFore [id=" + id + ", content=" + content + ", staffId="
				+ staffId + ", createdDt=" + createdDt + ", type=" + type
				+ ", targetId=" + targetId + ", extra=" + extra + ", staffName=" + staffName + ", headUrl=" + headUrl + ", messageType=" + messageType +"]";
	}
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
}
