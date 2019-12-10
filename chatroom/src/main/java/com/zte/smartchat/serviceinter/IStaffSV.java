package com.zte.smartchat.serviceinter;

import com.zte.smartchat.entity.Staff;

public interface IStaffSV {
	Staff getStaff(int staffId);
	Staff getStaff(String code);
	int updateHead(Staff staff);
	void UpdateStaff(Staff staff);
	int updateState(Staff staff);
}
