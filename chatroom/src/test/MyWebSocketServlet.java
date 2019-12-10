package xin.test;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class MyWebSocketServlet extends WebSocketServlet {  
	
	private static final long serialVersionUID = -7178893327801338294L;

	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("[事件：servlet被访问][request="+request);
		String ip = request.getRemoteAddr().toString();
		if(ip.equals(""))
		{
			ip="未知用户";
		}
		return new MyMessageInbound(ip);
	}

}