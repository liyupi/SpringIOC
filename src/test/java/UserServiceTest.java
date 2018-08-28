import com.yupi.service.UserService;
import com.yupi.spring.annotation.ExtClassPathXmlApplicationContext;
import com.yupi.spring.xml.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/8/27 22:35
 */
public class UserServiceTest {


    @Test
    public void XmlTest() throws Exception {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) app.getBean("userService");
        userService.init();
    }

    @Test
    public void AnnotationTest() throws Exception {
        ExtClassPathXmlApplicationContext app = new ExtClassPathXmlApplicationContext("com.yupi.service.impl");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.di();
    }
}