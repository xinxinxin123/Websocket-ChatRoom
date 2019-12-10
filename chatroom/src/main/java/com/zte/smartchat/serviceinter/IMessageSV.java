package com.zte.smartchat.serviceinter;

import java.util.List;

import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.service.MessageSVImp;

public interface IMessageSV {
	public int insertRecord(Message message);
	public List<MessageToFore> getPublicTop10();
	public List<MessageToFore> selectPublicNormal(int number);
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number);
	public List<MessageToFore> getRecords();
	public List<MessageToFore> getPrivateTop10(int staffId, int targetId);
	public List<MessageToFore> getPrivateRecords(int staffId, int targetId);
}