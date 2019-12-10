package xin.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zte.smartchat.dao.MessageDao;
import com.zte.smartchat.entity.Message;

/**
 * 测试从前台操作后台数据
 * @author liyuxin
 *
 */
@WebServlet("/CRUDServlet")
public class CRUDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	MessageDao messageDao = new MessageDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取标示信息，看要执行什么操作
		String tag = request.getParameter("tag");
		//查询操作
		if (tag.equals("select")) {
			//调用dao层的方法执行相应的操作,此处操作为查询所有消息
			List<Message> messages = messageDao.selectAll();
			//获取Json串
			String result = new Gson().toJson(messages);
			//将数据返回到前台
			response.getWriter().write(result);
		}
		//插入操作
		else if(tag.equals("insert")){
			//获取从前台传过来的表单数据
			String content = request.getParameter("content");
			String staffId = request.getParameter("staffId");
			String createdDt = request.getParameter("createdDt");
			String type = request.getParameter("type");
			
			//插入数据
//			Message message = new Message(content, Integer.parseInt(staffId),new Date(createdDt) , type);
//			messageDao.insertRecord(message);
//			
//			response.getWriter().write("insert success!!!");
		}
		//更新数据
		else if(tag.equals("update")){
			//获取前台的表单数据
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			String staffId = request.getParameter("staffId");
			String createdDt = request.getParameter("createdDt");
			String type = request.getParameter("type");
			
			//更新数据
//			Message message = new Message(Integer.parseInt(id),content, Integer.parseInt(staffId),new Date(createdDt) , type,"extra");
//			messageDao.updateByPrimaryKey(message);
//			response.getWriter().write("update success!!!");
		}
		 	  	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
