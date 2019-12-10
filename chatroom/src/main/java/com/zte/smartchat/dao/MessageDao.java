package com.zte.smartchat.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.util.Mybatisutil;

/**
 * message表的相关操作
 * @author liyuxin
 *
 */
public class MessageDao {
	/**
	 * 插入一条记录 ，其主键自增
	 * @param message
	 * @return
	 */
	public int insertRecord(Message message){
		/*getSqlSession(true) 表示自动提交*/
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.MessageMapper.insertSelective";
		session.insert(statement, message);
		session.close();
		return 1;
	}
	
	/**
	 * 查询所有消息记录
	 * @return 消息记录集合
	 */
	public List<Message> selectAll(){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectAll";
		List<Message> messages = session.selectList(statement);
		session.close();
		return messages;
	}
	
	/**
	 * 查询所有消息记录
	 */
	public List<MessageToFore> selectRecords(){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectRecords";
		List<MessageToFore> messages = session.selectList(statement);
		session.close();
		return messages;
	}
	/**
	 * 查询300消息记录
	 */
	public List<MessageToFore> selectRecords300(){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectRecords300";
		List<MessageToFore> messages = session.selectList(statement);
		session.close();
		return messages;
	}
	
	/**
	 * 查询私聊消息记录
	 */
	public List<MessageToFore> selectPrivateRecords(int staffId, int targetId){
		SqlSession session = Mybatisutil.getSqlSession();
		String statament = "com.zte.smartchat.mapper.MessageMapper.selectPrivateRecords";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("targetId", targetId);
		map.put("staffId", staffId);
		List<MessageToFore> messages = session.selectList(statament, map);
		session.close();
		return messages;
	}
	
	/**
	 * 查询私聊消息记录
	 */
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number){
		SqlSession session = Mybatisutil.getSqlSession();
		String statament = "com.zte.smartchat.mapper.MessageMapper.selectPrivateRecords";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("targetId", targetId);
		map.put("staffId", staffId);
		map.put("number", number);
		List<MessageToFore> messages = session.selectList(statament, map);
		session.close();
		return messages;
	}
	
	/**
	 * 通过主键删除一条数据
	 * @return 
	 */
	
	public int deleteByPrimaryKey(Long messageId){
		
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.MessageMapper.deleteByPrimaryKey";
		int res = session.delete(statement,String.valueOf(messageId));
		session.close();
		return res;
	}
	/**
	 * 通过主键删除多条数据
	 */
	public int delete(List<Integer> id) {
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.MessageMapper.deleteByPrimaryKeys";
		int res = session.delete(statement,id);
		session.commit();
		session.close();
		return res;
    }
	
	/**
	 * 通过STAFF_ID删除一条数据
	 * @return 
	 */
	
	public int deleteByStaff_id(int staff_id){
		
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.MessageMapper.deleteByStaff_id";
		int res = session.delete(statement,String.valueOf(staff_id));
		session.close();
		return res;
	}
	
	/**
	 * 通过消息id 找到消息 并修改消息数据
	 */
	public int updateByPrimaryKey(Message message){
		SqlSession session = Mybatisutil.getSqlSession(true);
		String statement = "com.zte.smartchat.mapper.MessageMapper.updateByPrimaryKeySelective";
		int res = session.update(statement, message);
		session.close();
		return res;
	}
	
	/**
	 * 取出message表中的前十条消息
	 */
	
	public List<MessageToFore> selectPublicTop10(){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectPublicTop10";
		List<MessageToFore>  tenMessages = session.selectList(statement);
		return tenMessages;
	}
	
	/**
	 * 取出message表中的前n条文本消息
	 */
	
	public List<MessageToFore> selectPublicNormal(int number){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectPublicNormal";
		List<MessageToFore>  tenMessages = session.selectList(statement, number);
		return tenMessages;
	}
	
	/**
	 * 取出私聊的前十条消息
	 * @param staffId
	 * @param targetId
	 * @return List<MessageToFore>
	 */
	public List<MessageToFore> selectPrivateTop10(int staffId, int targetId){
		SqlSession session = Mybatisutil.getSqlSession();
		String statement = "com.zte.smartchat.mapper.MessageMapper.selectPrivateTop10";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("targetId", targetId);
		map.put("staffId", staffId);
		List<MessageToFore> messages = session.selectList(statement, map);
		Collections.reverse(messages);
		session.close();
		return messages;
		
	}
}
