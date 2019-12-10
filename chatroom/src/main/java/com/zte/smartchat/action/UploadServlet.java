package com.zte.smartchat.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zte.smartchat.service.UploadFileSV;

/**
 * 上传文件以及上传发送的图片的servlet
 * @author liyuxin
 *
 */
public class UploadServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    
	UploadFileSV uploadFileSV = new UploadFileSV();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action_type = request.getParameter("action_type");
        if (action_type.equals("sendImage")) {
        	//  发送图片的相关处理
        	System.out.println("send Image");
        	uploadFileSV.sendImage(request, response);
			
		}else if(action_type.equals("uploadFile"))
		{
			//  上传文件的相关处理
			System.out.println("upload file");
			uploadFileSV.uploadFile(request, response);
			
		}else{
			System.out.println("action error");
		}
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	

}
