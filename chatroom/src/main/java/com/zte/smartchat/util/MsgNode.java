package com.zte.smartchat.util;

import com.zte.smartchat.entity.MessageToFore;

public class MsgNode {
	private MessageToFore msg;
	protected MsgNode pre;
	protected MsgNode next;
	
	public MsgNode(){
		
	}
	
	public MsgNode(MessageToFore message){
		this.setMsg(message);
	}

	public MessageToFore getMsg() {
		return msg;
	}

	public void setMsg(MessageToFore msg) {
		this.msg = msg;
	}
}
