package per.xin.chatroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import per.xin.chatroom.entity.MessageToFore;
import per.xin.chatroom.service.IMessageSV;
import per.xin.chatroom.service.impl.MessageSVImp;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private IMessageSV messageSV;

    @GetMapping(value = "/qryAllMessage")  //TODO 聊天记录分页
    public List<MessageToFore> qryMessgae(@RequestParam("staffId") Integer staffId, @RequestParam("targetId") Integer targetId) {

        List<MessageToFore> messages;
        if(0 == targetId){
            messages = messageSV.getRecords();
        }
        else {
            messages = messageSV.getPrivateRecords(staffId, Integer.valueOf(targetId));
        }

        return messages;
    }
}
