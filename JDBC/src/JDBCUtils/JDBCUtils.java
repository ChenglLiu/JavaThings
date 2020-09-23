package JDBCUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author liuclo
 * @Desciption 操作数据库的工具类
 * @Date  2020/9/20 16:00
 **/

public class JDBCUtils {
    /**
     * @author liuclo
     * @Desciption 获取连接
     * @Date  2020/9/20 16:00
     **/
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //1. 读取配置信息
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().
                getResourceAsStream("jdbc.properties");

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

        return connection;
    }

    /**
     * @author liuclo
     * @Desciption 资源关闭
     * @Date  2020/9/22 23:12
     **/
    public static void closeResource(Connection connection, Statement statement) {
        //PreparedStatement extends Statement
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
