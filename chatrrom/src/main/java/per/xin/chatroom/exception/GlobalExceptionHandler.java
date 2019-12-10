package per.xin.chatroom.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: <br>
 * 异常处理类
 * @author <br>
 * @taskId <br>
 */
@ControllerAdvice
public class GlobalExceptionHandler { // TODO 异常处理

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public String nullPointExceptionHandler(Exception e) {
        return "global nullPointExceptionHandler handler " + e.getClass();
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public  String exceptionHandler(Exception e) {
        return "global exception handler " + e.getClass();
    }
}
