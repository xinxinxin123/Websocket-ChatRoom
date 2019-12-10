package per.xin.chatroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.xin.chatroom.entity.SharedFile;
import per.xin.chatroom.mapper.SharedFileMapper;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
@RestController
@RequestMapping(value = "/shareFile")
public class ShareFileServlet {

    @Autowired
    SharedFileMapper sharedFileMapper;

    @GetMapping(value = "/getFileList")
    public List<SharedFile> getFileList() {
        return sharedFileMapper.selectAll();
    }
}
