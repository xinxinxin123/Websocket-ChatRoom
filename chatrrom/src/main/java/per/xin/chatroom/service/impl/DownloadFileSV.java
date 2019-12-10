package per.xin.chatroom.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import per.xin.chatroom.entity.SharedFile;
import per.xin.chatroom.mapper.SharedFileMapper;

@Service
public class DownloadFileSV {


	@Autowired
    private SharedFileMapper sharedFileMapper;

	@Value("${per.xin.sharefile.path}")
    private String shareFilePath;

	public DownloadFileSV() {
	}

	
	public boolean downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取fileId
		String fileId = request.getParameter("fileId").trim();
		//通过fileId查找文件信息
		SharedFile sharedFile = sharedFileMapper.selectByPrimaryKey(Integer.valueOf(fileId));
		
		//获取响应的输出流
		ServletOutputStream outputStream = response.getOutputStream();
		
		 //处理请求参数
        File fileToDownload=null;
        fileToDownload=new File( shareFilePath+ sharedFile.getBackName());
        
        
        //响应下载请求
        if (fileToDownload!=null)
        {
            String fileSavePath=fileToDownload.getAbsolutePath();
            System.out.println("将到这里查找文件:" + fileSavePath);
            
            if (!fileToDownload.exists())
            {
            	outputStream.print("File not found");
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
                    response.setHeader("Content-Disposition","attachment; filename=" + new String(sharedFile.getForeName().getBytes("utf-8"), "ISO-8859-1").trim());
                    if (fis != null)
                    {
                        int filelen = fis.available();
                        // 文件太大时内存不能一次读出,要循环
                        byte a[] = new byte[filelen];
                        fis.read(a);
                        outputStream.write(a);
                    }
                    fis.close();
                    outputStream.close();
                    System.out.println("文件下载成功");
                }
            }
        }
        
		return true;
	}
}
