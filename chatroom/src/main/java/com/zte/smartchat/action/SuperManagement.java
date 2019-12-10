package com.zte.smartchat.action;

import java.io.IOException;
import java.util.Random;

import javafx.scene.control.Alert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zte.smartchat.util.MD5Util;

/**
 * Servlet implementation class SuperManagement
 */
@WebServlet("/SuperManagement")
public class SuperManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuperManagement() {
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
		String referer = request.getHeader("Referer");
		System.out.println(referer);
		if(referer != null && referer.contains("SmartChat/chat.html")){
			request.getRequestDispatcher("manager.html").forward(request, response);
		}else{
			response.sendRedirect("noPermission.html");
		}
	}

}
