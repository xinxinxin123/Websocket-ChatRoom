package per.xin.chatroom.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.xin.chatroom.entity.Message;
import per.xin.chatroom.entity.MessageToFore;
import per.xin.chatroom.mapper.MessageMapper;
import per.xin.chatroom.service.IMessageSV;

@Service
public class MessageSVImp implements IMessageSV {
	
	@Autowired
	MessageMapper messageMapper;
	
	@Override
	public int insertRecord(Message message) {
		messageMapper.insertSelective(message);
		return 0;
	}
	
	/**
	 * 测试通过主键删除一条消息记录
	 * @param
	 */
	public void testDeleteByPrimary(long id){
		;
		messageMapper.deleteByPrimaryKey((long) id);
	}
	/**
	 * 通过主键删除多条消息记录
	 * 
	 */
	public void deleteByPrimaryIds(List<Integer> messages_id){
		;
		messageMapper.delete(messages_id);
	}
	/**
	 * 取出所有message消息
	 */
	public void testSelectAllFromMessage(){
		;
		List<Message> messages = messageMapper.selectAll();
		
		for (Message message : messages) {
			System.out.println(message.toString());
		}
	}
	/**
	 * 取出所有消息记录
	 */
	@Override
	public List<MessageToFore> getRecords() {
		;
		List<MessageToFore> records = messageMapper.selectRecords();
		return records;
	}
	/**
	 * 取出300消息记录
	 */
	public List<MessageToFore> getRecords300() {
		;
		List<MessageToFore> records = messageMapper.selectRecords300();
		return records;
	}
	/**
	 * 获取群聊前10条消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> getPublicTop10() {
		;
		List<MessageToFore> messages = messageMapper.selectPublicTop10();
		return messages;
	}
	
	/**
	 * 获取群聊前n条普通文本消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> selectPublicNormal(int number) {
		;
		List<MessageToFore> messages = messageMapper.selectPublicNormal(number);
		return messages;
	}
	
	/**
	 * 获取私聊前n条普通文本消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number) {
		;
		List<MessageToFore> messages = messageMapper.selectPrivateRecords(staffId, targetId, number);
		return messages;
	}
	/**
	 * 获取私聊的前十条消息
	 */
	@Override
	public List<MessageToFore> getPrivateTop10(int staffId, int targetId) {
		;
		return messageMapper.selectPrivateTop10(staffId, targetId);
	}
	/**
	 * 获取私聊的消息记录
	 */
	@Override
	public List<MessageToFore> getPrivateRecords(int staffId, int targetId) {
		;
		return messageMapper.selectPrivateRecords(staffId, targetId);
	}

}
