package per.xin.chatroom.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import per.xin.chatroom.exception.BaseAppException;
import per.xin.chatroom.service.IMessageSV;
import per.xin.chatroom.service.IStaffSV;
import per.xin.chatroom.service.impl.MessageSVImp;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
public class SessionInterceptor implements HandlerInterceptor{


    private static PersonalProperty personalProperty;

    private static PersonalProperty getPersonalProperty() {
        if (null == personalProperty) {
            personalProperty = ApplicationContextHolder.getBean(PersonalProperty.class);
        }
        return personalProperty;
    }

    private static List<String> canTouchRes = new ArrayList<>();

    private static List<String> demoModeUnTouchRes = new ArrayList<>();

    static {
        canTouchRes.add("/login.html");
        canTouchRes.add("/logout.html");
        canTouchRes.add("/logout.html");
        canTouchRes.add("/usr/login");
        canTouchRes.add("/");

        demoModeUnTouchRes.add("/usr/updatePwd");
    }

    /*对请求进行过滤*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String demoModel = getPersonalProperty().getDemoModel();
        if ("Y".equals(demoModel)) {
            if (demoModeUnTouchRes.contains(request.getRequestURI())) {
                throw new BaseAppException("SYS-MODE-0001", "演示模式不支持修改密码");
            }
        }
        //登陆 和 登出提示也放行
        if (canTouchRes.contains(request.getRequestURI())) {
            return true;
        }
        //重定向
        Object object = request.getSession().getAttribute("staffId");
        if (null == object) {
            response.sendRedirect("/logout.html");
            return false;
        }
        return true;

    }


}
