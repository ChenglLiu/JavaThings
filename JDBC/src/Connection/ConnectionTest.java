package Connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author liuclo
 * @Desciption //TODO
 * @Date  2020/9/19 22:11
 **/

public class ConnectionTest {
    //方式一
    @Test
    public void test01() throws SQLException {
        //获取Driver类的实现对象
        Driver driver = new com.mysql.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/myemployees";
        /*
        jdbc:协议
        mysql:子协议
        localhost:ip地址
        3306:端口号
        myemployees:数据库
         */

        //将用户名和密码封装到properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        Connection connection = driver.connect(url, info);

        System.out.println(connection);
    }

    //方式二：对方式一的迭代
    //不希望出现第三方库：com.mysql.jdbc.Driver()
    @Test
    public void test02() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
        //1. 获取Driver实现类对象：使用反射
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2. 提供连接的数据库
        String url = "jdbc:mysql://localhost:3306/myemployees";

        //3. 提供连接的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        //4. 获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式三：使用DriverManager替换Driver
    @Test
    public void test03() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
        //1. 获取Driver实现类的对象
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2. 提供三个连接基本信息：url、user、password
        String url = "jdbc:mysql://localhost:3306/myemployees";
        String user = "root";
        String password = "123456";

        //3. 注册驱动
        DriverManager.registerDriver(driver);

        //2. 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式四：只用加载驱动，不用显示注册驱动
    @Test
    public void test04() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
        //1. 提供三个连接基本信息：url、user、password
        String url = "jdbc:mysql://localhost:3306/myemployees";
        String user = "root";
        String password = "123456";

        //2. 加载Driver
        /*相比于方式三，可以省略注册驱动操作，是因为在mysql的Driver源码中，帮我们注册了驱动并实例化了Driver*/
        Class.forName("com.mysql.jdbc.Driver");

        //3. 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式五：将数据库连接的基本信息声明在配置文件中，通过读取配置文件的方式获取连接
    @Test
    public void test05() throws IOException, ClassNotFoundException, SQLException {
        //1. 读取配置文件信息
        InputStream resourceAsStream =
                ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2. 加载驱动
        Class.forName(driverClass);

        //3. 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
