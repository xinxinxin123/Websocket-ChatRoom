package com.zte.smartchat.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.service.StaffSVImpl;
import com.zte.smartchat.serviceinter.IStaffSV;
import com.zte.smartchat.util.MD5Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String code = request.getParameter("username");
		String pwd = request.getParameter("password");
		
		IStaffSV iStaffSV = StaffSVImpl.getInstance();
		Staff staff = iStaffSV.getStaff(code);
		
		PrintWriter pw = response.getWriter();
		
		if(null == staff){
			pw.print("用户不存在");
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
					pw.print("ok");
				}else if(staff.getState().equalsIgnoreCase("I")){
					pw.print("很抱歉，您的账号已被禁用，请联系管理员");
				}
				
			}
			else{
				pw.print("用户名或密码不匹配");
			}
		}
		
		pw.close();
	}

}