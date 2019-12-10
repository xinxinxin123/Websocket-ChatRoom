package per.xin.chatroom.entity;

import java.util.Date;

/**
 * 群共享文件 （与数据库中shared_file表对应）
 * @author TXZ
 *
 */
public class SharedFile {
	/**
	 * 文件id
	 */
    private Integer id;
    /**
     * 上传该文件的员工
     */
    private String uploader;
    /**
     * 上传时间
     */
    private Date createDate;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 前端显示的文件名
     */
    private String foreName;
    /**
     * 后台存储的文件名
     */
    private String backName;
    
    public SharedFile(String uploader, Date createDate, String fileType,
			String foreName, String backName) {
		super();
		this.uploader = uploader;
		this.createDate = createDate;
		this.fileType = fileType;
		this.foreName = foreName;
		this.backName = backName;
	}

	public SharedFile(Integer id, String uploader, Date createDate,
			String fileType, String foreName, String backName) {
		super();
		this.id = id;
		this.uploader = uploader;
		this.createDate = createDate;
		this.fileType = fileType;
		this.foreName = foreName;
		this.backName = backName;
	}

	public SharedFile() {
		super();
	}

	@Override
	public String toString() {
		return "SharedFile [id=" + id + ", uploader=" + uploader
				+ ", createDate=" + createDate + ", fileType=" + fileType
				+ ", foreName=" + foreName + ", backName=" + backName + "]";
	}

	

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName == null ? null : foreName.trim();
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName == null ? null : backName.trim();
    }
}