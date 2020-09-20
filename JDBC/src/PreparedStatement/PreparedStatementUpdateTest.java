package PreparedStatement;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author liuclo
 * @Desciption 使用PreparedStatement替换Statement，实现数据库表的CRUD操作
 * @Date  2020/9/20 15:23
 **/

public class PreparedStatementUpdateTest {
    //向jobs表中添加一条记录
    @Test
    public void test01() throws Exception {
        //1. 读取配置文件信息
        InputStream resourceAsStream = PreparedStatementUpdateTest.class.getClassLoader().
                getResourceAsStream("jdbc.properties");
        //ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

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
//        System.out.println(connection);

        //4. 预编译sql语句，返回PreparedStatement实例
        String sql = "insert into departments(department_id, department_name, " +
                "manager_id, location_id)values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //5. 填充占位符
        preparedStatement.setInt(1, 300);
        preparedStatement.setString(2, "IT");
        preparedStatement.setInt(3, 108);
        preparedStatement.setInt(4, 1700);

        //6. 执行sql操作
        preparedStatement.execute();

        //7. 资源关闭
        preparedStatement.close();
        connection.close();
    }
}
