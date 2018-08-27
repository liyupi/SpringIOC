import com.yupi.service.UserService;
import com.yupi.spring.ClassPathXmlApplicationContext;
import org.dom4j.DocumentException;
import org.junit.Test;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/8/27 22:35
 */
public class UserServiceTest {

    @Test
    public void print() throws Exception {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) app.getBean("userService");
        userService.print();
    }
}