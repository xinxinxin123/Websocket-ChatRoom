package per.xin.chatroom.entity;

import java.util.Date;

/**
 * 实体：聊天消息  与数据库中message表对应
 * @author TXZ
 *
 */
public class Message {
	/**
	 * 消息id
	 */
    protected Integer id;
    
    /**
     * 消息内容
     */
    protected String content;

    /**
     * 发信员工的id号
     */
    protected Integer staffId;

    /**
     * 发信时间
     */
    protected Date createdDt;

    /**
     * 消息类型
     */
    protected String type;

    /**
     * 聊天目标ID
     */
    protected Integer targetId;

    /**
     * 备用字段
     */
    protected String extra;
    
    public Message() {
		super();
	}

	public Message(Integer id, String content, Integer staffId, Date createdDt, String type, Integer targetId, String extra){
    	this.setId(id);
    	this.setContent(content);
    	this.setStaffId(staffId);
    	this.setCreatedDt(createdDt);
    	this.setType(type);
    	this.setTargetId(targetId);
    	this.extra = extra;
    }
    
    public Message(String content, Integer staffId, Date createdDt, String type, Integer targetId){
    	super();
    	this.setContent(content);
    	this.setStaffId(staffId);
    	this.setCreatedDt(createdDt);
    	this.setType(type);
    	this.setTargetId(targetId);
    }
    
    @Override
	public String toString() {
		return "PrivateMsg [id=" + id + ", content=" + content + ", staffId="
				+ staffId + ", createdDt=" + createdDt + ", type=" + type
				+ ", extra=" + extra + ", targetId=" + targetId + "]";
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}