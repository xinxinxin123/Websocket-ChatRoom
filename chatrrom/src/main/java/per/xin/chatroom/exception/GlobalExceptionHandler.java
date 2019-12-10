package per.xin.chatroom.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import per.xin.chatroom.entity.ErrorDetail;

/**
 * Description: <br>
 * 异常处理类
 * @author <br>
 * @taskId <br>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BaseAppException.class})
    @ResponseBody
    public ErrorDetail baseAppExceptionHandler(BaseAppException e) {
        logger.debug("业务异常", e);
        return new ErrorDetail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public  ErrorDetail exceptionHandler(Exception e) {
        logger.error("系统内部异常", e);
        return new ErrorDetail("SYSTEM-0001", "系统内部异常，请联系管理员");
    }
}
