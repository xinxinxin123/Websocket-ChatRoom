package com.zte.smartchat.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.zte.smartchat.entity.SharedFile;
import com.zte.smartchat.util.Mybatisutil;

public class SharedFileDao {
	/**
	 * 插入一条记录
	 * @param file
	 * @return
	 */
	public int insertRecord(SharedFile file){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.SharedFileMapper.insertSelective";
		int res = session.insert(statement, file);
		session.close();
		return res;
		
	}
	/**
	 * 通过主键查询一条数据
	 * @param fileId
	 * @return
	 */
	public SharedFile selectOne(Long fileId){
		
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.SharedFileMapper.selectByPrimaryKey";
		SharedFile sharedFile = session.selectOne(statement, fileId);
		return sharedFile;
	}
	
	public List<SharedFile> selectAll(){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.SharedFileMapper.selectAll";
		List<SharedFile> sharedFiles = session.selectList(statement);
		return sharedFiles;
	}
}
