package com.zte.smartchat.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.entity.Staff;
import com.zte.smartchat.service.MessageSVImp;
import com.zte.smartchat.service.StaffSVImpl;

/**
 * Servlet implementation class AllStaffServlet
 */
@WebServlet("/ManageServlet")
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String tag = request.getParameter("tag");
		StaffSVImpl staffSVImpl = StaffSVImpl.getInstance();
		MessageSVImp messageSVImp = MessageSVImp.getInstance();
		//查询操作
		if (tag.equals("select")) {			
			List<Staff> staffs = staffSVImpl.SelectAllFromStaff();
			String json = new Gson().toJson(staffs);
			//System.out.println(json);
			response.getWriter().write(json);
		}else if(tag.equals("insert")){
			String code = request.getParameter("code");
			String name = request.getParameter("username");
			name = URLDecoder.decode(name,"UTF-8");
			String pwd = request.getParameter("pwd");
			String state = request.getParameter("state");
			String power = request.getParameter("isAdminister");
			Staff staff = new Staff(name, code, pwd,
					"", power, "", state);			
			staffSVImpl.testInsertIntoStaff(staff);
			response.getWriter().write(1);
		}else if(tag.equals("delete")){
			String staff_id = request.getParameter("staff_id");
			int id = Integer.parseInt(staff_id);
			staffSVImpl.DeleteStaffById(id);
			response.getWriter().write("delete success");
		}else if(tag.equals("modify")){
			String id = request.getParameter("id");
			int id0 = Integer.parseInt(id);
			String code = request.getParameter("code");
			String name = request.getParameter("username");
			String pwd = request.getParameter("pwd");
			String state = request.getParameter("state");
			String power = request.getParameter("isAdminister");
			Staff staff = new Staff(id0,name,code, pwd,
					"", power, "", state);			
			staffSVImpl.UpdateStaff(staff);
			System.out.println(staff);
		}else if(tag.equals("selectOne")){
			String staff_id = request.getParameter("staff_id");
			System.out.println(staff_id);
			int id = Integer.parseInt(staff_id);
			Staff staff = staffSVImpl.getStaff(id);
			String json = new Gson().toJson(staff);
			response.getWriter().write(json);
		}else if(tag.equals("allMessage")){
			
			List<MessageToFore> messageToFore = messageSVImp.getRecords300();
			String json = new Gson().toJson(messageToFore);
			response.getWriter().write(json);
			System.out.println(json);
		}else if(tag.equals("deleteMessage")){
			String id = request.getParameter("message_id");
			Long id0 = Long.parseLong(id);
			messageSVImp.testDeleteByPrimary(id0);
		}else if(tag.equals("deleteMessages")){
			String[] messages_id = request.getParameterValues("messages_id[]");
			System.out.println("**********************************");
			List<Integer> ids=new ArrayList<Integer>();
			//int[] ids = new int[messages_id.length];
			for(int i=0;i<messages_id.length;i++){
				//int.TryParse(messages_id[i], out ids[i]);
				ids.add(Integer.parseInt(messages_id[i]));
			}
			System.out.println(ids);
			//List<String> messages_id = Arrays.asList(ids);
			messageSVImp.deleteByPrimaryIds(ids);
		}
	}
}
