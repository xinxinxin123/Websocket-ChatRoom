package per.xin.chatroom.exception;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
public class BaseAppException extends RuntimeException {

    private String errorCode;

    public BaseAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseAppException(String errorCode, Throwable cause, String message) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
