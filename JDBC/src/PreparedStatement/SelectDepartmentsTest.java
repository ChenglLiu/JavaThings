package PreparedStatement;

import JDBCUtils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * @author liuclo
 * @Desciption 对表departments的查询操作
 * @Date  2020/9/23 22:01
 **/

public class SelectDepartmentsTest {
    @Test
    public void test01() throws Exception {
        //1. 获取连接
        Connection con = JDBCUtils.getConnection();

        //2. 预编译sql语句，返回PreparedStatement实例
        String sql = "select * from departments where department_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        //3. 填充占位符
        ps.setObject(1, 300);

        //4. 执行，并返回结果集
        ResultSet resultSet = ps.executeQuery();

        //5. 处理结果集
        if (resultSet.next()) {     //next():判断结果集下一条是否有数据，返回true，指针自动下移；返回false，指针不下移
            //获取当前数据的字段值
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int manager = resultSet.getInt(3);
            int location = resultSet.getInt(4);

            //方式一：
            System.out.println("department_id: " + id + ", department_name: " + name +
                    ", manager_id: " + manager + ", location_id: " + location);

            //方式二：
            Object[] data = new Object[]{id, name, manager, location};
            System.out.println(Arrays.toString(data));

            //方式三：创建类
        }
        //6. 关闭资源
    }
}
