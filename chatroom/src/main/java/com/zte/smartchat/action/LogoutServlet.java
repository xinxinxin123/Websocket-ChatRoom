package com.zte.smartchat.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
		
		
		String reason = request.getParameter("reason");
		if(null == reason || reason.equalsIgnoreCase("logout")){
			response.sendRedirect("login.html");
		}else if(reason.equalsIgnoreCase("othersLogin")){
			response.sendRedirect("othersLogin.html");
		}else if(reason.equalsIgnoreCase("kick")){
			response.sendRedirect("kickedout.html");
		}else{
			response.sendRedirect("login.html");
		}
	}

}
