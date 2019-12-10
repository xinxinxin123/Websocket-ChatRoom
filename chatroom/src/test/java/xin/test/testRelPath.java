package xin.test;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class testRelPath
 */
@WebServlet("/testRelPath")
public class testRelPath extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testRelPath() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String basePath = request.getContextPath();  
//		basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath+"/";
//		System.out.println(basePath);
//	     String servletPath =  request.getServletPath();  
	     String realPath=request.getRealPath("")+"//";  
	     String realitivePath = request.getRealPath("/");
;//	     System.out.println(servletPath);
	     System.out.println(realPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
