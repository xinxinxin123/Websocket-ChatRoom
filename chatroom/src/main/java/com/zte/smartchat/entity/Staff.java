package com.zte.smartchat.entity;
/**
 * 员工 （与数据库中staff表对应）
 * @author TXZ
 *
 */
public class Staff {
	/**
	 * 员工id
	 */
    private Integer id;
    
    /**
     * 员工姓名
     */
    private String name;
    
    /**
     * 员工工号
     */
    private String code;
    
    /**
     * 密码
     */
    private String pwd;
    
    /**
     * 头像URL地址
     */
    private String headUrl;
    /**
     * 用户权限 是否为管理员  
     * S：Super管理员  N：Normal普通员工
     */
    private String power;
    
    /**
     * 备用字段
     */
	private String extra;
    /**
     * 状态 : A：在用；I：停用
     */
    private String state;
    
  //用于传到前台展示
  	public Staff(Integer id, String name, String headUrl, String power, String state){
  		this.id = id;
  		this.name = name;
  		this.headUrl = headUrl;
  		this.power = power;
  		this.state = state;
  	}
  	
    public Staff() {
		super();
	}

	public Staff(String name, String code, String pwd,
			String headUrl, String power, String extra, String state) {
		super();
		this.name = name;
		this.code = code;
		this.pwd = pwd;
		this.headUrl = headUrl;
		this.power = power;
		this.extra = extra;
		this.state = state;
	}
	public Staff(Integer id, String name, String code, String pwd,
			String headUrl, String power, String extra, String state) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.pwd = pwd;
		this.headUrl = headUrl;
		this.power = power;
		this.extra = extra;
		this.state = state;
	}
	

	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", code=" + code
				+ ", pwd=" + pwd + ", headUrl=" + headUrl + ", power=" + power
				+ ", extra=" + extra + ", state=" + state + "]";
	}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}