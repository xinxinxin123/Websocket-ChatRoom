package com.zte.smartchat.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.util.Mybatisutil;
/**
 * 表staff相关操作
 * @author liyuxin
 *
 */
public class StaffDao {
	/**
	 * 查询所有职员信息录
	 * @return 职员信息集合
	 */
	public List<Staff> selectAll(){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.StaffMapper.selectAll";
		List<Staff> staff = session.selectList(statement);
		session.close();
		return staff;
	}
	/**
	 * 通过主键获取一条记录
	 */
	public Staff selectOne(int id){
		/*获取session*/
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.StaffMapper.selectByPrimaryKey";
		Staff staff = session.selectOne(statement,id);
		session.close();
		return staff;
		/*关闭session*/
	}
	
	/**
	 * 通过工号获取一条记录
	 */
	public Staff selectOne(String code){
		/*获取session*/
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.StaffMapper.selectByCode";
		Staff staff = session.selectOne(statement,code);
		session.close();
		return staff;
		/*关闭session*/
	}
	/**
	 * 通过id修改职员信息
	 */
	public int updateByPrimaryKey(Staff staff){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.updateByPrimaryKeySelective";
		int res = session.update(statement, staff);
		session.close();
		return res;
	}
	/**
	 * 通过主键删除一条职员信息
	 * @return 
	 */
	
	public int deleteStaffByPrimaryKey(int staffId){
		
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.deleteByPrimaryKey";
		int res = session.delete(statement,String.valueOf(staffId));
		session.close();
		return res;
	}
	/**
	 * 通过工号删除一条职员信息
	 * @return 
	 */
	
	public int deleteStaffByCode(String code){
		
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.deleteByCode";
		int res = session.delete(statement,String.valueOf(code));
		session.close();
		return res;
	}
	/**
	 * 通过插入一条记录，主键递增
	 * @param 
	 * @return
	 */
	public int insertRecord(Staff staff){
		/*getSqlSession(true) 表示自动提交*/
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.insertSelective";
		int res = session.insert(statement, staff);
		session.close();
		return res;
	}
	
	/**
	 * 根据用户id,更改用户的头像路径
	 */
	public int updateHeadUrlById(Staff staff){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.updateHeadUrl";
		int res = session.update(statement, staff);
		return res;
	}
	
	/**
	 * 根据用户id,更改用户的状态
	 */
	public int updateStateById(Staff staff){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.StaffMapper.updateState";
		int res = session.update(statement, staff);
		return res;
	}
}
