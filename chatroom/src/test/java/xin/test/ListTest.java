package xin.test;

import java.util.ArrayList;
import java.util.List;

import com.zte.smartchat.entity.Staff;

public class ListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Staff> staffs = new ArrayList<Staff>();
		
//		Staff s1 = new Staff("123","123","123","123");
		
//		staffs.add(s1);
		
//		s1.setCode("333");
		
		
		System.out.print(staffs.get(0).getCode());
	}

}
