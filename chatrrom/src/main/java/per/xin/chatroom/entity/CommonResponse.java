package per.xin.chatroom.entity;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
public class CommonResponse {

    public CommonResponse() {
    }

    public CommonResponse(String responseCode, String responseDesc) {
        this.responseCode = responseCode;
        this.responseDesc = responseDesc;
    }

    private String responseCode;

    private String responseDesc;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
