package per.xin.chatroom.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import per.xin.chatroom.entity.Staff;
import per.xin.chatroom.entity.dto.LoginReq;
import per.xin.chatroom.service.IStaffSV;
import per.xin.chatroom.util.MD5Util;

/**
 * Servlet implementation class LoginServlet
 */
@RestController
@RequestMapping(value = "/usr")
public class LoginController{

	@Autowired
	private IStaffSV staffSV;


	@PostMapping(value = "/login")
	public String loginIn(@RequestBody LoginReq loginReq, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String code = loginReq.getUsername();
		String pwd = loginReq.getPassword();

		Staff staff = staffSV.getStaff(code);

		if(null == staff){
			return "用户不存在";
		}
		else{
			String md5Pwd = MD5Util.getMD5Str(staff.getPwd(), null);
			if(md5Pwd.equals(pwd)){
				if(staff.getState().equalsIgnoreCase("A") || staff.getState().equalsIgnoreCase("X")){
					HttpSession session = request.getSession();
					session.setAttribute("name", staff.getName());
					session.setAttribute("staffId", staff.getCode());

					Cookie username = new Cookie("username", URLEncoder.encode(staff.getName(), "utf-8"));
					username.setPath("/SmartChat");
					response.addCookie(username);
					return "ok";
				}else if(staff.getState().equalsIgnoreCase("I")){
					return "很抱歉，您的账号已被禁用，请联系管理员";
				}
				else {
					return "ok";
				}

			}
			else{
				return "用户名或密码不匹配";
			}
		}

	}

	@GetMapping(value = "/logout")
	public void logout(@RequestParam("reason")String reason,  HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++){
			if(cookies[i].getName().equals("username")){
				cookies[i].setMaxAge(-1);
				cookies[i].setValue(null);

				cookies[i].setPath("/SmartChat");
				response.addCookie(cookies[i]);
				break;
			}
		}

		request.getSession().removeAttribute("name");
		request.getSession().removeAttribute("staffId");

		if(null == reason || reason.equalsIgnoreCase("logout")){
			response.sendRedirect("/login.html");
		}else if(reason.equalsIgnoreCase("othersLogin")){
			response.sendRedirect("/othersLogin.html");
		}else if(reason.equalsIgnoreCase("kick")){
			response.sendRedirect("/kickedout.html");
		}else{
			response.sendRedirect("/login.html");
		}
	}


	@PostMapping(value = "/updateHead")
	public void uploadHead(@RequestBody Staff staff) {
		staffSV.updateHead(staff); //TODO 异常处理
	}

	@GetMapping(value = "/updatePwd")
	public Map<String, String> updatePwd(@RequestParam("staffId") Integer staffId, @RequestParam("pwd_old") String oldPwd,
						 @RequestParam("pwd_new") String newPwd) {

		Map<String, String> response = new HashMap<>();
		Staff staff = staffSV.getStaff(staffId);
		if (staff.getPwd().equals(oldPwd)) {
			staff.setPwd(newPwd);
			staffSV.UpdateStaff(staff);
			response.put("res", "修改成功，请重新登录");
		}else{
			response.put("res", "原密码输入错误");
		}
		return response;
	}


}