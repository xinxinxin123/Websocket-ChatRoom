package com.zte.smartchat.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.service.StaffSVImpl;
import com.zte.smartchat.serviceinter.IStaffSV;

/**
 * Servlet implementation class VcardServlet
 */
@WebServlet("/VcardServlet")
public class VcardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Gson gsonUtil = new Gson();
    IStaffSV staffSV = StaffSVImpl.getInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VcardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String pwd_old = request.getParameter("pwd_old");
		String pwd_new = request.getParameter("pwd_new");
		String staffId = request.getParameter("staffId");
		Staff staff = staffSV.getStaff(Integer.parseInt(staffId));
		if (staff.getPwd().equals(pwd_old)) {
			staff.setPwd(pwd_new);
			staffSV.UpdateStaff(staff);
			response.getWriter().write("{\"res\":\"修改成功，请重新登录\"}");
		}else{
			response.getWriter().write("{\"res\":\"原密码输入错误\"}");
		}
		System.out.println(pwd_old);
		System.out.println(pwd_new);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String staffJson = request.getParameter("body");
		Staff staff = gsonUtil.fromJson(staffJson, Staff.class);
		staffSV.updateHead(staff);
	}

}
