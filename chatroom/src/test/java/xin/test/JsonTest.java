package xin.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.entity.Staff;
/**
 * json相关测试
 * @author liyuxin
 *
 */
public class JsonTest {

	@Test
	public void test() {
		
	}

	/**
	 * object2Json
	 */
	@Test
	public void testObject2Json(){
		Gson gson = new Gson();
//		Staff staff = new Staff("liyuxin", "11", "2222", "A");
//		String jsonString = gson.toJson(staff);
//		System.out.println(jsonString);
	}
	
	/**
	 * Json2Object
	 */
	@Test
	public void testJson2Object(){
		String jsonString = "{\"name\":\"liyuxin\",\"code\":\"11\",\"pwd\":\"2222\",\"state\":\"A\"}";
		Gson gson = new Gson();
		Staff staff = gson.fromJson(jsonString, Staff.class);
		System.out.println(staff.toString());
	}
	
	/**
	 * 测试转型  向上转型  MessageToFore  to  Messgae
	 */
	@Test
	public void testMessage(){
//		Message message = new Message(1, "lyx", 779, new Date());
//		MessageToFore fore = new MessageToFore();
//		fore = (MessageToFore) message;
//		System.out.println(fore.toString());
		
		
		MessageToFore messageToFore = new MessageToFore();
		messageToFore.setContent("aaaaa");
		messageToFore.setCreatedDt(new Date());
		messageToFore.setMessageType(1);
		messageToFore.setStaffId(11);
		messageToFore.setStaffName("liyuxin");
		
		Message message = messageToFore;
		System.out.println(message.toString());
	}
	
	
	/**
	 * 测试List2Json
	 */
	@Test
	public void TestList2Json(){
		Staff staff1 = new Staff(1, "lyx", "1", "1", "asudh", "A", "asdasd", "asd");
		Staff staff2 = new Staff(1, "lixoapw", "1", "1", "asudh", "A", "asdasd", "asd");
		List<Staff> list = new ArrayList<Staff>();
		list.add(staff1);
		list.add(staff2);
		String reString = new Gson().toJson(list);
		System.out.println(reString);
	}
	
}
