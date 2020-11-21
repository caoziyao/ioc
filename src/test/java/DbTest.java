import java.sql.*;
import java.util.Random;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public class DbTest {
    public static final String URL = "jdbc:mysql://49.234.12.16:3306/user";
    public static final String USER = "root";
    public static final String PASSWORD = "zk123n456";

    private static Connection conn = null;
    static{
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2. 获得数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

    /**
     * 更新
     */
    public void update(String username, int age) throws SQLException {
        //获取连接
        Connection conn = DbTest.getConnection();
        //sql, 每行加空格
        String sql = "UPDATE user set username=?, age=? where id=?";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setString(1, username);
        ptmt.setInt(2, age);
        ptmt.setInt(3, 1);

        //执行
        ptmt.execute();
    }

    /**
     * 增加
     * @throws SQLException
     */
    public void add(String username, int age) throws SQLException {
        //获取连接
        Connection conn = DbTest.getConnection();
        //sql
        String sql = "INSERT INTO user(username, age) values(" + "?, ?)";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setString(1, username);
        ptmt.setInt(2, age);

        //执行
        ptmt.execute();
    }

    /**
     * 查询
     * @throws SQLException
     */
    public void query() throws SQLException {
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT username, age FROM user");
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs.getString("username")+" 年龄："+rs.getInt("age"));
        }
    }

    /**
     * 删除
     * @throws Exception
     */
    public void del(int id) throws SQLException {
        //获取连接
        Connection conn = DbTest.getConnection();
        //sql, 每行加空格
        String sql = "delete from user where id=?";
        //预编译SQL，减少sql执行
        PreparedStatement ptmt = conn.prepareStatement(sql);
        //传参
        ptmt.setInt(1, id);
        //执行
        ptmt.execute();
    }

    public static void main(String[] args) throws Exception {
        DbTest t = new DbTest();
        t.query();
        System.out.println("---更新操作---");
        t.update("zhangsan", 20);
        t.query();
        System.out.println("---添加操作---");
        double random = Math.random();  // 产生一个[0，1)之间的随机数
        t.add("wang" + random, 17);
        t.query();
        System.out.println("---删除---");
        // del();
    }
}
