package xin.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**'
 * 下载文件
 * @author liyuxin
 *
 */
@WebServlet(name = "download111", urlPatterns = { "/download11111" })
public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 System.out.println("\n");
	        System.out.println("====收到下载文件请求====");
	        
	        ServletContext context = this.getServletContext();
	        System.out.println(context.getAttribute("name"));
	        
	        SimpleDateFormat formatBuilder = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        System.out.println("时间" + formatBuilder.format(new Date()));
	        
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html");
	        ServletOutputStream out = response.getOutputStream();
	        
	        String ip = request.getRemoteAddr();
	        String port = String.valueOf(request.getRemotePort());
	        System.out.println("请求来自" + ip + ":" + port);
	        
	        String paramFileName = new String(request.getParameter("filename").getBytes("UTF-8"), "UTF-8").toString();
	        System.out.println("参数filename:" + paramFileName);
	        
	        
	        //处理请求参数
	        File fileToDownload=null;
	     
	        String DIR_DATAOUT_MAIN =
			        "D:\\dev_workspace\\webSocket\\SmartChat\\WebContent\\files\\";
	        
	        //String RELATIVE_PATH = "..\\webapps\\SmartChat\\files\\";
            fileToDownload=new File( DIR_DATAOUT_MAIN+ paramFileName);
	        
	        
	        
	        //响应下载请求
	        if (fileToDownload!=null)
	        {
	            String fileSavePath=fileToDownload.getAbsolutePath();
	            System.out.println("将到这里查找文件:" + fileSavePath);
	            
	            if (!fileToDownload.exists())
	            {
	                out.print("File not found");
	                System.out.println("文件不存在!");
	            }else {
	             // 读取文件流
	                FileInputStream fis = new FileInputStream(fileToDownload);
	                // 下载文件
	                // 设置响应头和下载保存的文件名
	                if (fileSavePath != null && fileSavePath.length() > 0)
	                {
	                    // 设置响应为下载文件
	                    response.setContentType("application/x-msdownload");
	                    // 设置响应文件名为路径
	                    response.setHeader("Content-Disposition",
	                        "attachment; filename=" + new String(paramFileName.getBytes("utf-8"), "utf-8") + "");
	                    if (fis != null)
	                    {
	                        int filelen = fis.available();
	                        // 文件太大时内存不能一次读出,要循环
	                        byte a[] = new byte[filelen];
	                        fis.read(a);
	                        out.write(a);
	                    }
	                    fis.close();
	                    out.close();
	                    System.out.println("文件下载成功");
	                }
	            }
	        }
	        System.out.println("======完成请求======");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
