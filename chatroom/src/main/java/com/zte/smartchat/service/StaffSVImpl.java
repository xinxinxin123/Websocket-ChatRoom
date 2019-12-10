package com.zte.smartchat.service;

import java.util.List;
import java.util.logging.Level;

import com.zte.smartchat.action.WebSocketTest;
import com.zte.smartchat.dao.MessageDao;
import com.zte.smartchat.dao.StaffDao;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.serviceinter.IStaffSV;
import com.zte.smartchat.util.LogManager;

public class StaffSVImpl implements IStaffSV {
	
	private StaffSVImpl() {}  
    private static final StaffSVImpl single = new StaffSVImpl();  
    //静态工厂方法   
    public static StaffSVImpl getInstance() {  
        return single;  
    }

	@Override
	public Staff getStaff(int staffId) {
		StaffDao dao  = new StaffDao();
		Staff staff = dao.selectOne(staffId);
		return staff;
	}

	@Override
	public Staff getStaff(String code) {
		// TODO Auto-generated method stub
		StaffDao dao = new StaffDao();
		Staff staff = dao.selectOne(code);
		return staff;
	}
	/**
	 * 取出所有staff记录
	 */
	public List<Staff> SelectAllFromStaff(){
		StaffDao dao = new StaffDao();
		List<Staff> staffs = dao.selectAll();		
		for (Staff staff : staffs) {
			System.out.println(staff.toString());
		}
		return staffs;
	}
	/**
	 * 测试通过工号获取某个职员信息
	 * @return 
	 */
	public Staff SelectStaff2(String code) {
		StaffDao dao  = new StaffDao();
		Staff staff = dao.selectOne(code);
		return staff;
	}
	/**
	 * 插入职员信息     (自动生成主键) 
	 */
	public void testInsertIntoStaff(Staff staff){
		StaffDao dao = new StaffDao();
		dao.insertRecord(staff);
	}
	/**
	 * 测试通过主键修改职员信息
	 */
	public void UpdateStaff(Staff staff){
		StaffDao dao = new StaffDao();
		int res = dao.updateByPrimaryKey(staff);
		System.out.println(res);
	}
	/**
	 * 通过主键删除职员信息（先删消息，再删职员）
	 * 
	 */
	public void DeleteStaffById(int id){
		try {
			
			MessageDao messageDao = new MessageDao();
			messageDao.deleteByStaff_id(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"删除职员信息失败",e);
		}
		StaffDao staffDao  = new StaffDao();
		staffDao.deleteStaffByPrimaryKey(id);
	}

	@Override
	public int updateHead(Staff staff) {
		StaffDao dao = new  StaffDao();
		int res = dao.updateHeadUrlById(staff);
		return res;
	}
	
	/**
	 * 测试通过主键修改职员状态
	 */
	@Override
	public int updateState(Staff staff) {
		StaffDao dao = new  StaffDao();
		int res = dao.updateStateById(staff);
		return res;
	}
}
