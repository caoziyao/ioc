import com.flask.mybatis.session.SqlSessionFactory;
import com.flask.mybatis.session.SqlSessionFactoryBuilder;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class MyBatisTest {
    public static void main(String[] args) {
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build("application.properties");
    }
}
