package com.zte.smartchat.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;

//A queue like a circle
public class QueueCircle {
	private int size;
	private MsgNode start, current;
	private final Lock lock = new ReentrantLock();
	
	
	public QueueCircle(int sz){
		this.setStart(new MsgNode());
		this.setCurrent(this.getStart());
		
		for(int i=1; i<sz; i++){
			MsgNode msgN = new MsgNode();
			this.current.next = msgN;
			msgN.pre = this.current;
			this.current = msgN;
		}
		
		
		this.start.pre = this.current;		
		this.current = this.current.next = this.start;
		//this.current = this.start;
		
		this.setSize(sz);
	}
	
	public Message cover(MessageToFore msg){

		lock.lock();
		MsgNode msgN = new MsgNode(msg);
		try{
			msgN.getMsg().setCreatedDt(new Date());
			this.current.setMsg(msgN.getMsg());
			
			this.current = this.current.next;
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCicle error:",e);
		}finally{
			lock.unlock();
		}

		return msg;
	}
	
	public void remove(){
		this.current.next.pre = this.current.pre;
		this.current.pre.next = this.current.next;
		this.current = this.current.next;
		this.size--;
	}
	
	public void remove(MessageToFore msg){
		lock.lock();
		MsgNode msgN = new MsgNode(msg);
		try{
			MsgNode currentTemp = this.start;
			for(int i=0; i<this.size; i++){			
				if(msgN.getMsg().toString().equals(currentTemp.getMsg().toString())){
					currentTemp.setMsg(null);
				}
				currentTemp = currentTemp.next;
			}
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCircle remove error:",e);
		}finally{
			lock.unlock();
		}
	}
	
	public Message insert(MessageToFore msg){
		lock.lock();
		MsgNode msgN = new MsgNode(msg);

		try{
			if(null == this.current.getMsg()){
				MsgNode currentTemp = this.current;
				for(int i=0; i<this.size; i++){				
					if(msgN.getMsg().getCreatedDt().after(null == currentTemp.pre.getMsg()?
							new Date(0) :currentTemp.pre.getMsg().getCreatedDt())){
						msgN.next =currentTemp;
						msgN.pre = currentTemp.pre;
						msgN.pre.next =msgN;
						msgN.next.pre = msgN;
						this.size++;
						this.remove();
						break;
					}
					currentTemp = currentTemp.pre;					
				}
			}else{
				//only Insert the latest message
				if(msgN.getMsg().getCreatedDt().after(this.current.getMsg().getCreatedDt())){
					MsgNode currentTemp = this.current;
					for(int i=0; i<=this.size; i++){
						if(msgN.getMsg().getCreatedDt().before(currentTemp.getMsg().getCreatedDt())
								|| i == this.size){
							msgN.next = currentTemp;
							msgN.pre = currentTemp.pre;
							msgN.pre.next =msgN;
							msgN.next.pre = msgN;
							this.size++;
							break;
						}
						currentTemp = currentTemp.next;
					}
					this.remove();
				}				
			}
			//display();
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCircle insert error:",e);
		}finally{
			lock.unlock();
		}

		return msg;
	}
	
	public void insert(List<MessageToFore> msgList){
		for(int i=0; i<msgList.size(); i++){			
			this.insert(msgList.get(i));			
		}
	}
	
	public void clear(){
		lock.lock();
		try{
			this.start.setMsg(null);
			this.current = this.current.next;
			while(this.current != this.start){
				this.current.setMsg(null);
				this.current = this.current.next;
			}
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCircle clear error:",e);
		}finally{
			lock.unlock();
		}
	}
	
	public void display(){
		System.out.println("打印线程启动");		
		try{
			for(int i=0; i<this.size; i++){
				System.out.println(i+":"+this.current.getMsg());
				this.current = this.current.next;
			}
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCircle display error:",e);
		}finally{
			
		}
		System.out.println("打印线程关闭");
	}
	
	public List<MessageToFore> getMessages(){
		lock.lock();
		List<MessageToFore> msgs = new ArrayList<MessageToFore>();
		try{
			MsgNode currentTemp = this.current;
			for(int i=0; i<this.size; i++){
				if(null != currentTemp.getMsg()){
					msgs.add(currentTemp.getMsg());
				}
				currentTemp = currentTemp.next;
			}
		}catch(Exception e){
			//e.printStackTrace();
			LogManager.getLogger(MD5Util.class).log(Level.WARNING,"QueueCircle getMessages error:",e);
		}finally{
			lock.unlock();
		}
		return msgs;
	}
	
	public int getEleNum(){
		int m = 0;
		MsgNode currentTemp = this.current;
		for(int i=0; i<this.size; i++){
			if(null != currentTemp.getMsg()){
				m++;
			}
			currentTemp = currentTemp.next;
		}
		return m;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public MsgNode getStart() {
		return start;
	}
	public void setStart(MsgNode start) {
		this.start = start;
	}

	public MsgNode getCurrent() {
		return current;
	}
	public void setCurrent(MsgNode current) {
		this.current = current;
	}
}
