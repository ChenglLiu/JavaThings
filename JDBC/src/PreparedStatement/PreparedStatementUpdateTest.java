package PreparedStatement;

import JDBCUtils.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author liuclo
 * @Desciption 使用PreparedStatement替换Statement，实现数据库表的CRUD操作
 * @Date  2020/9/20 15:23
 **/

public class PreparedStatementUpdateTest {
    /**
     * @author liuclo
     * @Desciption 通用的增删改操作
     * @Date  2020/9/23 20：34
     **/
    public void update(String sql, Object ...args) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //1. 获取数据库连接
            con = JDBCUtils.getConnection();

            //2. 预编译sql语句，返回PreparedStatement实例
            ps = con.prepareStatement(sql);

            //3. 填充占位符
            for (int i=0; i<args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            //4. 执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5. 资源关闭
            JDBCUtils.closeResource(con, ps);
        }
    }

    /**
     * @author liuclo
     * @Desciption //TODO
     * @Date  2020/9/23
     **/


    //向departments表中添加一条记录
    @Test
    public void test01() {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
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
            connection = DriverManager.getConnection(url, user, password);

            //4. 预编译sql语句，返回PreparedStatement实例
            String sql = "insert into departments(department_id, department_name, " +
                    "manager_id, location_id)values(?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            //5. 填充占位符
            ps.setInt(1, 300);
            ps.setString(2, "IT");
            ps.setInt(3, 108);
            ps.setInt(4, 1800);

            //6. 执行sql操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7. 资源关闭
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //修改departments表的一条记录
    @Test
    public void test02() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1. 获取数据库的连接
            connection = JDBCUtils.getConnection();

            //2. 预编译sql语句，返回PreparedStatement的实例
            String sql = "update departments set manager_id = ? where department_id = ?";
            preparedStatement = connection.prepareStatement(sql);

            //3. 填充占位符
            preparedStatement.setObject(1, null);
            preparedStatement.setObject(2, 300);

            //4. 执行
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //5. 资源关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }

    @Test
    public void test03() {
        String sql = "update departments set manager_id = ? where department_id = ?";
        update(sql, 108, 300);

        sql = "delete from departments where department_id = ?";
        update(sql, 300);
    }
}
