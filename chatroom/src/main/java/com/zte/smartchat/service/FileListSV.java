package com.zte.smartchat.service;

import java.util.List;

import com.zte.smartchat.dao.SharedFileDao;
import com.zte.smartchat.entity.SharedFile;

public class FileListSV {
	private SharedFileDao sharedFileDao = new SharedFileDao();
	
	public List<SharedFile> getFIleList(){
		List<SharedFile> sharedFiles = sharedFileDao.selectAll();
		return sharedFiles;
	}
}
