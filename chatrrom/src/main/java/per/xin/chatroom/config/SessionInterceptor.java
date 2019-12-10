package per.xin.chatroom.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
public class SessionInterceptor implements HandlerInterceptor{

    private static List<String> canTouchRes = new ArrayList<>();

    static {
        canTouchRes.add("/login.html");
        canTouchRes.add("/logout.html");
        canTouchRes.add("/logout.html");
        canTouchRes.add("/usr/login");
        canTouchRes.add("/");
    }

    /*对请求进行过滤*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
