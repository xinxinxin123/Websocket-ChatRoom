SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS staff;


/* Create Tables */

-- MESSAGE
CREATE TABLE message
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT '消息ID',
	CONTENT varchar(4000) NOT NULL COMMENT '内容',
	STAFF_ID int unsigned NOT NULL COMMENT '员工ID',
	CREATED_DT timestamp NOT NULL COMMENT '创建时间',
	-- N:普通消息  I：图片消息
	TYPE CHAR(1) NOT NULL COMMENT '消息类型',
	TARGET_ID int unsigned NOT NULL COMMENT '聊天目标ID',
	EXTRA VARCHAR(20) COMMENT '备用字段',
	PRIMARY KEY (ID)	
) COMMENT = 'MESSAGE';


-- 员工
CREATE TABLE staff
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT '员工ID',
	NAME varchar(20) NOT NULL COMMENT '姓名',
	CODE varchar(20) NOT NULL COMMENT '工号',
	PWD varchar(20) NOT NULL COMMENT '密码',
	HEAD_URL varchar(100) COMMENT '头像URL',
	-- S：Super管理员  N：Normal普通员工
	POWER char(1) NOT NULL COMMENT '用户权限',
	EXTRA VARCHAR(20) COMMENT '备用字段',
	-- A：在用；I：停用
	STATE char(1) NOT NULL COMMENT '状态 : A：在用；I：停用',
	PRIMARY KEY (ID)
) COMMENT = '员工';

-- 文件表
DROP TABLE IF EXISTS shared_file;
CREATE TABLE shared_file (
  `ID` int(10) unsigned NOT NULL auto_increment COMMENT '文件id',
  `UPLOADER` varchar(20) NOT NULL COMMENT '上传文件的员工',
  `CREATE_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '创建时间',
  `FILE_TYPE` varchar(10) NOT NULL COMMENT '文件类型',
  `FORE_NAME` varchar(50) NOT NULL COMMENT '前端显示的文件名',
  `BACK_NAME` varchar(50) NOT NULL COMMENT '后端存储的文件名',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件';

/* Create Foreign Keys */

ALTER TABLE message
	ADD FOREIGN KEY (STAFF_ID)
	REFERENCES staff (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
