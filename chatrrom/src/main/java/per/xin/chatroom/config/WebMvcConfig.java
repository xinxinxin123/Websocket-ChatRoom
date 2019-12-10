package per.xin.chatroom.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: <br>
 *
 * @author <br>
 * @taskId <br>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 设置首页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login.html");
    }

    /**
     * 设置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns(Arrays.asList("/css/**",
                "/js/**","/images/**","/img/**", "/emoj/**", "/bootstrap/**", "/html/**", "/emoj/**", ""))
        ;
    }
}
