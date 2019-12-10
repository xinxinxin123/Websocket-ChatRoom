package com.zte.smartchat.action;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zte.smartchat.entity.MessageToFore;
import com.zte.smartchat.service.MessageSVImp;
import com.zte.smartchat.serviceinter.IMessageSV;

/**
 * Servlet implementation class QueryMessages
 */
@WebServlet("/QueryMessages")
public class QueryMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryMessages() {
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
		int staffId = Integer.valueOf(request.getParameter("staffId"));
		int targetId = Integer.valueOf(request.getParameter("targetId"));
		System.out.println(staffId);
		
		System.out.println(targetId);
		String recs;
		Gson gson = new Gson();
		int i=0;
		IMessageSV iMessageSV = MessageSVImp.getInstance();
		List<MessageToFore> messages;
		if(0 == targetId){
			messages = iMessageSV.getRecords();
			System.out.println("puclic");
		}
		else {
			messages = iMessageSV.getPrivateRecords(staffId, Integer.valueOf(targetId));
		}
		for(MessageToFore message : messages){
			i++;
			System.out.println(i+message.toString());
		}
		recs = gson.toJson(messages);
		System.out.println(recs);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(recs);
	}

}
