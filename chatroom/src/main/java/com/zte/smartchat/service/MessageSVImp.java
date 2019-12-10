package com.zte.smartchat.service;


import java.util.List;



import com.zte.smartchat.dao.MessageDao;
import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.serviceinter.IMessageSV;

public class MessageSVImp implements IMessageSV {
	
	private MessageSVImp() {}  
    private static final MessageSVImp single = new MessageSVImp();  
    //静态工厂方法   
    public static MessageSVImp getInstance() {  
        return single;  
    }  
	
	@Override
	public int insertRecord(Message message) {
		MessageDao dao = new MessageDao();
		dao.insertRecord(message);
		return 0;
	}
	
	/**
	 * 测试通过主键删除一条消息记录
	 * @param 消息Id
	 */
	public void testDeleteByPrimary(long id){
		MessageDao dao = new MessageDao();
		dao.deleteByPrimaryKey((long) id);
	}
	/**
	 * 通过主键删除多条消息记录
	 * 
	 */
	public void deleteByPrimaryIds(List<Integer> messages_id){
		MessageDao dao = new MessageDao();
		dao.delete(messages_id);
	}
	/**
	 * 取出所有message消息
	 */
	public void testSelectAllFromMessage(){
		MessageDao dao = new MessageDao();
		List<Message> messages = dao.selectAll();
		
		for (Message message : messages) {
			System.out.println(message.toString());
		}
	}
	/**
	 * 取出所有消息记录
	 */
	@Override
	public List<MessageToFore> getRecords() {
		MessageDao dao = new MessageDao();
		List<MessageToFore> records = dao.selectRecords();
		return records;
	}
	/**
	 * 取出300消息记录
	 */
	public List<MessageToFore> getRecords300() {
		MessageDao dao = new MessageDao();
		List<MessageToFore> records = dao.selectRecords300();
		return records;
	}
	/**
	 * 获取群聊前10条消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> getPublicTop10() {
		MessageDao dao = new MessageDao();
		List<MessageToFore> messages = dao.selectPublicTop10();
		return messages;
	}
	
	/**
	 * 获取群聊前n条普通文本消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> selectPublicNormal(int number) {
		MessageDao dao = new MessageDao();
		List<MessageToFore> messages = dao.selectPublicNormal(number);
		return messages;
	}
	
	/**
	 * 获取私聊前n条普通文本消息记录
	 * @return 
	 * 
	 */
	@Override
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number) {
		MessageDao dao = new MessageDao();
		List<MessageToFore> messages = dao.selectPrivateNormal(staffId, targetId, number);
		return messages;
	}
	/**
	 * 获取私聊的前十条消息
	 */
	@Override
	public List<MessageToFore> getPrivateTop10(int staffId, int targetId) {
		MessageDao dao = new MessageDao();
		return dao.selectPrivateTop10(staffId, targetId);
	}
	/**
	 * 获取私聊的消息记录
	 */
	@Override
	public List<MessageToFore> getPrivateRecords(int staffId, int targetId) {
		MessageDao dao = new MessageDao();
		return dao.selectPrivateRecords(staffId, targetId);
	}

}
