package xin.test;


import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.zte.smartchat.dao.MessageDao;
import com.zte.smartchat.dao.StaffDao;
import com.zte.smartchat.entity.Message;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.service.StaffSVImpl;
import com.zte.smartchat.serviceinter.IStaffSV;

public class MybatisTest {

	@Test
	public void testAll(){
//    	IStaffSV iStaffSV = new StaffSVImpl();
//    	Staff staff = iStaffSV.getStaff(1);
//    	System.out.println(staff.toString());
	}
	
	/**
	 * 测试通过主键获取某个职员信息
	 */
	@Test
	public void testSelectStaff() {
		StaffDao dao  = new StaffDao();
		Staff staff = dao.selectOne(4);
		System.out.println(staff.toString());
	}
	/**
	 * 测试通过工号获取某个职员信息
	 */
	@Test
	public void testSelectStaff2() {
		StaffDao dao  = new StaffDao();
		Staff staff = dao.selectOne("5");
		System.out.println(staff.toString());
	}
	/**
	 * 测试通过主键修改职员信息
	 */
	@Test 
	public void testUpdateStaff(){
		StaffDao dao = new StaffDao();
		Staff staff = new Staff(5, "小强", "1", "2", "I","e", "y","2");
		int res = dao.updateByPrimaryKey(staff);
		System.out.println(res);
	}
	/**
	 * 测试通过工号删除职员信息（先删消息，再删职员）
	 * 
	 */
	@Test
	public void testDeleteStaffByPrimary(){
		String code = "1";
		StaffDao staffDao  = new StaffDao();
		try {
			Staff staff = staffDao.selectOne(code);
			int id = staff.getId();//通过工号得到staff_id
			MessageDao messageDao = new MessageDao();
			messageDao.deleteByStaff_id(id);//通过staff_id删除message记录
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		staffDao.deleteStaffByCode(code);
	}
	/**
	 * message表插入一条记录
	 */
	@Test 
	public void testInsertIntoMessage(){
		MessageDao dao = new MessageDao();
		//Message message = new Message(1, "你好", 2, new Date(), "I", "打篮球");
		//dao.insertRecord(message);
	}
	
	/**
	 * 测试通过主键删除一条消息记录
	 * @param 消息Id
	 */
	@Test
	public void testDeleteByPrimary(){
		MessageDao dao = new MessageDao();
		dao.deleteByPrimaryKey((long) 23);
	}
	/**
	 * 测试通过STAFF_ID删除一条消息记录
	 * @param 
	 */
	@Test
	public void testDeleteBySTAFF_ID(){
		MessageDao dao = new MessageDao();
		dao.deleteByStaff_id(4);
	}
	
	/**
	 * 取出所有message消息
	 */
	@Test
	public void testSelectAllFromMessage(){
		MessageDao dao = new MessageDao();
		List<Message> messages = dao.selectAll();
		
		for (Message message : messages) {
			System.out.println(message.toString());
		}
	}
	
	/**
	 * 测试通过主键修改一条message数据
	 */
	@Test 
	public void testUpdate(){
		MessageDao dao = new MessageDao();
//		Message message = new Message(23, "呵呵呵", 4, new Date(), "I", "我喜欢打篮球");
//		int res = dao.updateByPrimaryKey(message);
//		System.out.println(res);
	}
	
	/**
	 * 测试查询最新的10条MessageToFore数据
	 */
	@Test 
	public void testGetTop10(){
		MessageDao dao = new MessageDao();
		List<MessageToFore> res = dao.selectPublicTop10();
		System.out.println(res);
	}
	
}
