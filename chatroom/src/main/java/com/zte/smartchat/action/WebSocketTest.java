package com.zte.smartchat.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.entity.MsgJson;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.entity.StaffToFore;
import com.zte.smartchat.service.MessageSVImp;
import com.zte.smartchat.service.StaffSVImpl;
import com.zte.smartchat.serviceinter.IMessageSV;
import com.zte.smartchat.serviceinter.IStaffSV;
import com.zte.smartchat.util.Constant;
import com.zte.smartchat.util.GetHttpSessionConfigurator;
import com.zte.smartchat.util.LogManager;
import com.zte.smartchat.util.QueueCircle;
 
@ServerEndpoint(value = "/websocket",configurator=GetHttpSessionConfigurator.class)
public class WebSocketTest {
 
    private static Hashtable<Integer, WebSocketTest> connections = new Hashtable<Integer, WebSocketTest>();
    /*连接时唯一对应一个员工*/
    private  Staff staff;
    private Session session;
    private HttpSession httpSession;
    private String nickname;
    private static QueueCircle msgQue;
    private static IMessageSV iMessageSV = MessageSVImp.getInstance();
    private static IStaffSV iStaffSV = StaffSVImpl.getInstance();
    //private boolean isOpen;     
    
    //初始化操作
    Gson jsonUtil = new Gson();
    public WebSocketTest() {
    	//isOpen = true;
    }
    
    @OnOpen
	public void start(Session session, EndpointConfig config) {
    	
    	this.session = session;
		this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
		this.nickname = this.httpSession.getAttribute("name").toString();
		String staffId = this.httpSession.getAttribute("staffId").toString();
		
		
    	Staff staff = iStaffSV.getStaff(staffId);
    	this.staff = staff;
    	
    	
    	checkLogin(this.staff.getId());		
    	connections.put(this.staff.getId(), this);
		
    	List<MessageToFore> msgs = msgQue.getMessages();
    	//将消息缓存传给前台
    	for(int i=0; i<msgs.size(); i++){
			msgs.get(i).setMessageType(Constant.MESSAGE_HISTORY);
    	}
    	try {
			this.session.getBasicRemote().sendText(jsonUtil.toJson(msgs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.SEVERE, "客户端打开时消息缓存发送至客户端失败", e);
		}
    	
    	
    	StaffToFore initMsg = new StaffToFore(this.staff.getId(), this.staff.getName(), this.staff.getHeadUrl(), this.staff.getPower(),this.staff.getState(), Constant.MESSAGE_SIGNAL);
    	try {
    		//将用户信息和用户列表传给前台
			this.session.getBasicRemote().sendText(jsonUtil.toJson(initMsg));
			this.session.getBasicRemote().sendText(jsonUtil.toJson(getConnectionStaff(Constant.MESSAGE_OPEN)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.SEVERE, "用户信息或用户列表发送至客户端失败", e);
		} 
    	loginoutNotice(Constant.MESSAGE_OPEN);
	}
 
    @OnClose
    public void end() { 
    	//this.isOpen = false;
    	connections.remove(this.staff.getId(),this);
    	loginoutNotice(Constant.MESSAGE_CLOSE);
    	System.out.println("连接已断开");     	
    }
 
    @OnMessage
    public void onMessage(String message) {
    	 // TODO: 过滤输入的内容
    	MsgJson msgJson = jsonUtil.fromJson(message, MsgJson.class);
    	switch (msgJson.getTargetType()) {
    	//1.取私聊消息记录
		case "initPrivate":			
    		List<MessageToFore> privateMsgToFores = iMessageSV.getPrivateTop10(this.staff.getId(), msgJson.getTargetId());

    		//将私聊消息记录发送至客户端
    		for(int i=0; i<privateMsgToFores.size(); i++){
        		privateMsgToFores.get(i).setMessageType(Constant.MESSAGE_HISTORY);
        		//System.out.println(privateMsgToFores.get(i).toString());
        	}
    		try {
    			this.session.getBasicRemote().sendText(jsonUtil.toJson(privateMsgToFores));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			//e.printStackTrace();
    			LogManager.getLogger(getClass()).log(Level.SEVERE, "私聊消息记录发送至客户端失败", e);
    		}
			break;
		//2.取群聊消息记录
		case "initPublic":
			List<MessageToFore> msgs = msgQue.getMessages(); 
	    	for(int i=0; i<msgs.size(); i++){	    		
				msgs.get(i).setMessageType(Constant.MESSAGE_HISTORY);
	    		//System.out.println(msgs.get(i).toString());
	    	}
	    	try {
				this.session.getBasicRemote().sendText(jsonUtil.toJson(msgs));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				LogManager.getLogger(getClass()).log(Level.SEVERE, "群聊消息记录发送至客户端失败", e);
			}
	    	break;
	    //3.发送消息
		case "send":
			//3.1群消息
			if (msgJson.getTargetId()==0) {
				 MessageToFore messageToFore = insertAndReturnForeMessage(msgJson);
	    		//插入消息到消息环队列
	    		msgQue.insert(messageToFore);
	    		//传到前台
	    		broadCast(jsonUtil.toJson(messageToFore));
			}
			//3.2私聊消息
			else {
				MessageToFore messageToFore = insertAndReturnForeMessage(msgJson);
				whisper(messageToFore);
			}
			break;
		//踢人消息   
		case "changeState":
			//通知客户端退出登陆  并将数据库该用户状态设为禁用
			if(msgJson.getTargetId() != 0){
				MessageToFore stateMsg = new MessageToFore();
				stateMsg.setMessageType(Constant.MESSAGE_NORMAL);
				stateMsg.setTargetId(0);
				stateMsg.setCreatedDt(new Date());
				msgQue.insert(stateMsg);
				Staff staff = connections.get(msgJson.getTargetId()).staff;
				staff.setState(msgJson.getMessage());
				iStaffSV.updateState(staff);
				if(msgJson.getMessage().equalsIgnoreCase("I")){
					stateMsg.setType("K");
					stateMsg.setContent(staff.getCode()+"-"+iStaffSV.getStaff(msgJson.getTargetId()).getName()
							+"已被管理员踢下线");
					try {
						connections.get(msgJson.getTargetId()).session.getBasicRemote().sendText("kick");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					broadCast(jsonUtil.toJson(stateMsg));
				}else if(msgJson.getMessage().equalsIgnoreCase("X")){
					stateMsg.setType("X");
					stateMsg.setContent(staff.getCode()+"-"+iStaffSV.getStaff(msgJson.getTargetId()).getName()
							+"已被管理员禁言");
					try {
						connections.get(msgJson.getTargetId()).session.getBasicRemote().sendText("silence");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					broadCast(jsonUtil.toJson(stateMsg));
				}
				
			}
			break;
		//上传文件消息
		case "upload_success":
			MessageToFore messageToFore = insertAndReturnForeMessage(msgJson);
			broadCast(jsonUtil.toJson(messageToFore));
			break;
		default:
			break;
		}
    }
    /**
     * 向数据库插入数据，并且返回记录
     * @param msgJson
     * @return
     */
    private MessageToFore insertAndReturnForeMessage(MsgJson msgJson){
    	String type = msgJson.getType();
    	String msg = msgJson.getMessage();
    	int targetId = msgJson.getTargetId();
		MessageToFore msgToFore = new MessageToFore(staff.getHeadUrl(),nickname, Constant.MESSAGE_NORMAL, msg, staff.getId(), new Date(), type, targetId);
				
		Message createMessage =  msgToFore;
		iMessageSV.insertRecord(createMessage);
		return msgToFore;
	}
    
    /**
     * 广播消息
     * @param jsonString 广播的消息体
     */
    private void broadCast(String jsonString){
    	Enumeration<WebSocketTest> enSocket = connections.elements();
    	while(enSocket.hasMoreElements()){
    		WebSocketTest client = enSocket.nextElement();
    		try {
				synchronized (client) {
					if(client.session.isOpen()){
						client.session.getBasicRemote().sendText(jsonString);
					}
				}
			} catch (IOException e) {
				LogManager.getLogger(getClass()).log(Level.SEVERE, "广播消息发送至id为"+client.staff.getId()+"的前台失败", e);
				//System.out.println("Chat Error: Failed to send message to client");
				connections.remove(client.staff.getId(), client);
			}
    	}
    }
    /**
     * 发送私聊消息
     * @param messageToFore 私聊的消息体
     */
    private void whisper(MessageToFore messageToFore ){
    	String messageToForeJson = jsonUtil.toJson(messageToFore);
    	try {    		
			this.session.getBasicRemote().sendText(messageToForeJson);
			if(this.staff.getId() != messageToFore.getTargetId()){
				connections.get(messageToFore.getTargetId()).session.getBasicRemote().sendText(messageToForeJson);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.SEVERE, "私聊消息由id为"+this.staff.getId()+
					"的员工发送至id为"+messageToFore.getTargetId()+"的员工前台失败", e);
		}
    }
    
   /**
    * 获取传输到前台的在线用户
    * @return
    */
    private List<StaffToFore> getConnectionStaff(int messgaeType){
    	List<StaffToFore> staffList = new ArrayList<StaffToFore>(); 
    	Enumeration<WebSocketTest> enSocket = connections.elements();
    	Staff staffTemp;
    	while(enSocket.hasMoreElements()){
    		WebSocketTest client = enSocket.nextElement();
    		staffTemp = client.staff;
    		StaffToFore staffToFore = new StaffToFore(staffTemp.getId(),staffTemp.getName(), staffTemp.getHeadUrl(), staffTemp.getPower(), this.staff.getState(),messgaeType);

			staffList.add(staffToFore);
    	}
    	return staffList;
    }
    
    /**
     * 通知用户上下线时同步用户列表
     * 
     */
    public void loginoutNotice(int messgaeType){
    	StaffToFore staffToFore = new StaffToFore(this.staff.getId(), this.staff.getName(), this.staff.getHeadUrl(), this.staff.getPower(), this.staff.getState(), messgaeType);
    	broadCast(jsonUtil.toJson(staffToFore));
    }
    
    @OnError
    public void onError(Throwable t) throws Throwable {
        //System.out.println("Chat Error: " );
		connections.remove(this.staff.getId(),this);
        //t.printStackTrace();
        LogManager.getLogger(getClass()).log(Level.SEVERE, "id为"+this.staff.getId()+"的WebSocket对象出错，服务器严重错误", t);        
    }
 
    /**
     * 检查用户是否重复登录
     * 
     */
    private void checkLogin(int stfId){
    	if(connections.containsKey(stfId)){
    		try {
				connections.get(stfId).session.getBasicRemote().sendText("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				LogManager.getLogger(getClass()).log(Level.WARNING,"用户重复登录时通知先前登录的用户下线失败",e);
			}
    		connections.remove(stfId);
    	}
    }
    
    /**
     * 初始化消息队列
     * 
     */
    public static void initMsgQue(){
    	msgQue = new QueueCircle(Constant.MAX_CAPACITY);
    	List<MessageToFore> msgList= iMessageSV.getPublicTop10();
    	msgQue.insert(msgList);
    }
    
    public static QueueCircle getMsgQue(){
    	return msgQue;
    }
    
    public Staff getStaff(){
    	return staff;
    }
    
    public Session getSession(){
    	return session;
    }
    
    public static Hashtable<Integer, WebSocketTest> getConnections(){
    	return connections;
    }
}