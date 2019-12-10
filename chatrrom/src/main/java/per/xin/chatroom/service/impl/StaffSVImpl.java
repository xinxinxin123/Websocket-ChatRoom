package per.xin.chatroom.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.xin.chatroom.entity.Staff;
import per.xin.chatroom.mapper.StaffMapper;
import per.xin.chatroom.service.IStaffSV;

@Service
public class StaffSVImpl implements IStaffSV {
	
	@Autowired
	private StaffMapper staffMapper;

	@Override
	public Staff getStaff(int staffId) {
		
		Staff staff = staffMapper.selectByPrimaryKey(staffId);
		return staff;
	}

	@Override
	public Staff getStaff(String code) {
	
		Staff staff = staffMapper.selectByCode(code);
		return staff;
	}
	/**
	 * 取出所有staff记录
	 */
	public List<Staff> SelectAllFromStaff(){
		
		List<Staff> staffs = staffMapper.selectAll();		
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
		
		Staff staff = staffMapper.selectByCode(code);
		return staff;
	}
	/**
	 * 插入职员信息     (自动生成主键) 
	 */
	public void testInsertIntoStaff(Staff staff){
		
		staffMapper.insertRecord(staff);
	}
	/**
	 * 测试通过主键修改职员信息
	 */
	public void UpdateStaff(Staff staff){
		
		int res = staffMapper.updateByPrimaryKey(staff);
		System.out.println(res);
	}
	/**
	 * 通过主键删除职员信息（先删消息，再删职员）
	 * 
	 */
	/*public void DeleteStaffById(int id){
		try {
			
			MessageDao messageDao = new MessageDao();
			messagestaffMapper.deleteByStaff_id(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogManager.getLogger(WebSocketTest.class).log(Level.WARNING,"删除职员信息失败",e);
		}
		StaffDao staffDao  = new StaffDao();
		staffstaffMapper.deleteStaffByPrimaryKey(id);
	}*/

	@Override
	public int updateHead(Staff staff) {
		int res = staffMapper.updateHeadUrl(staff);
		return res;
	}
	
	/**
	 * 测试通过主键修改职员状态
	 */
	@Override
	public int updateState(Staff staff) {
		
		int res = staffMapper.updateStateById(staff);
		return res;
	}
}
