package xin.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;*/

/**
 * 处理文件上传
 * @author liyuxin
 *
 */
@WebServlet("/upload11111111")
public class UploadServlet extends HttpServlet
{
    
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        
        // 存储文件相对路径,即相对于WebContent目录的路径
        String relativeFilePath = "";
        // 当前上传文件的InputStream对象
        InputStream is = null;
        List<FileItem> items;
        InputStream stream2 = null;
        
        // 下面的代码开始使用Commons-UploadFile组件处理上传的文件数据
        FileItemFactory factory = new DiskFileItemFactory(); // 建立FileItemFactory对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        
        // 分析请求，并得到上传文件的FileItem对象
        try
        {
            items = upload.parseRequest(new ServletRequestContext(request));
            // 循环处理上传文件
            for (FileItem item : items)
            {
                // 处理普通的表单域
                if (item.isFormField())
                {
                    try
                    {
                        System.out.println(item.getFieldName() + item.getString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    if (item.getFieldName().equals("filename"))
                    {
                        // 如果新文件不为空，将其保存在filename中
                        if (!item.getString().equals(""))
                            relativeFilePath = item.getString("UTF-8");
                        System.out.println("filename=" + relativeFilePath.replace("\\\\", "\\"));
                    }
                }
                // 处理上传文件
                else if (item.getName() != null && !item.getName().equals(""))
                {
                    // 从客户端发送过来的上传文件路径中截取文件名
                    String fileName = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
                    System.out.println("文件名:" + fileName);
                    //保存之前预处理
                    relativeFilePath = "img\\messageImg\\"+item.getName();
                    is = item.getInputStream(); // 得到上传文件的InputStream对象
                    
                    
                    //将输入流缓存，这样便可以重复读取
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                    byte[] buffer = new byte[1024];  
                    int len;  
                    while ((len = is.read(buffer)) > -1 ) {  
                        baos.write(buffer, 0, len);  
                    }  
                    baos.flush();                
                      
                    InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());  
                      
                    //TODO:显示到前台  
                      
                    stream2 = new ByteArrayInputStream(baos.toByteArray());  
                    
                    
                    //获取md5码
                    try {
						String md5 = MD5Test.getMD5Byinput(stream1);
						relativeFilePath = "img\\messageImg\\"+md5+fileName.substring(fileName.lastIndexOf("."));
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						stream2.close();
					}
                    
                    
                }
            }
        }
        catch (FileUploadException e1)
        {
            e1.printStackTrace();
        }
        
        // Servlet存放文件的根目录
        //String webContentPath = this.getServletContext().getRealPath("/");
        // 将路径和上传文件名组合成完整的服务端路径
        String DIR_DATAOUT_MAIN =
		        "D:\\dev_workspace\\webSocket\\SmartChat\\WebContent\\";
        
//        String DIR_DATAOUT_MAIN =
//        		"D:\\JAVA\\apache-tomcat-7.0.67\\webapps\\SmartChat\\";
        String fileSavePath = DIR_DATAOUT_MAIN + relativeFilePath;
        System.out.println("文件将会保存到这里:" + fileSavePath);
        
        File file=new File(fileSavePath);
        saveFile(stream2,file);
        System.out.println("======完成请求======");
        //返回数据
        String ImgUrl = relativeFilePath.replace("\\", "/");
        response.getWriter().write(ImgUrl);
        
        
    }
    
    private boolean saveFile(InputStream is,File file){
        // 检测文件目录目录是否存在,不存在就创建
        String filePath=file.getAbsolutePath();
        String fileDir = filePath.substring(0, filePath.lastIndexOf("\\"));// 获取文件目录
        if (!new File(fileDir).exists())
        {
            new File(fileDir).mkdirs();
        }
        
        // 如果服务器已经存在和上传文件同名的文件
        if (new File(filePath).exists())
        {
            System.out.println("文件已存在,不作任何操作,这个概率比1/2^128还要小哦,快去买彩票吧");
            //new File(fileDir).delete();
            return true;
        }      
        else
        {
            // 开始保存文件
            if (!filePath.equals(""))
            {
                // 用FileOutputStream打开服务端的上传文件
                FileOutputStream fos;
                try
                {
                    fos = new FileOutputStream(filePath);
                    byte[] buffer = new byte[1024]; // 每次读1KB字节
                    int count = 0;
                    // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                    while ((count = is.read(buffer)) > 0)
                    {
                        fos.write(buffer, 0, count); // 向服务端文件写入字节流
                    }
                    fos.close(); // 关闭FileOutputStream对象
                    is.close(); // InputStream对象
                    System.out.println("文件保存成功");
                    return true;
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    System.out.println("找不到文件");
                    return false;
                }catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("无法保存文件");
                    return false;
                }
            }
        }
        return false;
    }
}