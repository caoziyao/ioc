import com.flask.framework.AppConfig;
import com.flask.framework.FlaskApplicationContext;
import com.flask.service.OrderService;
import com.flask.service.UserService;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/20
 */
public class Test {

    public static void main(String[] args) throws Exception {
        // Classpath
        // 1，扫描
        // 2，实例化 （Bean的生命周期） 依赖注入
        FlaskApplicationContext context = new FlaskApplicationContext(AppConfig.class);
        OrderService orderService = (OrderService)context.getBean("orderService");
        System.out.println(orderService);
        orderService.test();

    }
}
