package per.xin.chatroom;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import per.xin.chatroom.entity.Staff;
import per.xin.chatroom.mapper.StaffMapper;

@ServletComponentScan
@SpringBootApplication
public class ChatrromApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ChatrromApplication.class, args);
		/*StaffMapper staffMapper = context.getBean(StaffMapper.class);
		List<Staff> staff = staffMapper.selectAll();
		System.out.println(staff);
*/

	}

}
