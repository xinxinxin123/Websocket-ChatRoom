package per.xin.chatroom.service;


import java.util.List;

import org.springframework.stereotype.Service;

import per.xin.chatroom.entity.Message;
import per.xin.chatroom.entity.MessageToFore;


public interface IMessageSV {
	public int insertRecord(Message message);
	public List<MessageToFore> getPublicTop10();
	public List<MessageToFore> selectPublicNormal(int number);
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number);
	public List<MessageToFore> getRecords();
	public List<MessageToFore> getPrivateTop10(int staffId, int targetId);
	public List<MessageToFore> getPrivateRecords(int staffId, int targetId);
}