package per.xin.chatroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description: <br>
 * 存放自定义的一些属性
 * @author <br>
 * @taskId <br>
 */
@Component
public class PersonalProperty {

    @Value("${per.xin.demo.mode}")
    private String demoModel;

    public String getDemoModel() {
        return demoModel;
    }

    public void setDemoModel(String demoModel) {
        this.demoModel = demoModel;
    }
}
