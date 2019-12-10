package xin.test;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
/**
 * 测试webSocket传参数
 * @author liyuxin
 *
 */
@ServerEndpoint(value = "/param/{name}")
public class WebSocketParamTest {
	
	@OnMessage
	public void onMessage(String msg,@PathParam("name") String name) {
		System.out.println("name");
	}

	@OnOpen
	public void start(Session session) {
	}

}
