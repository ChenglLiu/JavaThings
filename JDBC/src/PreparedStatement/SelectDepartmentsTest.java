package PreparedStatement;

import JDBCUtils.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
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
        String sql = "select * from departments where department_name = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        //3. 填充占位符
        ps.setObject(1, "IT");

        //4. 执行，并返回结果集
        ResultSet resultSet = ps.executeQuery();

        //5. 处理结果集
        while (resultSet.next()) {     //next():判断结果集下一条是否有数据，返回true，指针自动下移；返回false，指针不下移
            //获取当前数据的字段值
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int manager = resultSet.getInt(3);
            int location = resultSet.getInt(4);

            //方式一：
            System.out.println("department_id: " + id + ", department_name: " + name +
                    ", manager_id: " + manager + ", location_id: " + location);

            System.out.println("********");
            //方式二：
            Object[] data = new Object[]{id, name, manager, location};
            System.out.println(Arrays.toString(data));

            System.out.println("********");
            //方式三：创建类
            Departments departments = new Departments(id, name, manager, location);
            System.out.println(departments.toString());

            System.out.println();
        }
        //6. 关闭资源
        JDBCUtils.closeResource(con, ps, resultSet);
    }

    @Test
    public void test02() {
        String sql = "select * from departments where department_id = ?";
        Departments departments = queryForDepartments(sql, 300);
        System.out.println(departments);

        System.out.println("--------------------");
        sql = "select department_id, manager_id from departments where department_name = ?";
        departments = queryForDepartments(sql, "IT");
        System.out.println(departments);
    }

    /**
     * @author liuclo
     * @Desciption 针对departments表的通用查询操作
     * @Date  2020/9/24 20：30
     **/
    public Departments queryForDepartments(String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            //获取连接
            connection = JDBCUtils.getConnection();

            //预编译sql语句，返回PreparedStatement对象
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            result = ps.executeQuery();

            //获取结果的元数据
            ResultSetMetaData metaData = result.getMetaData();

            //通过ResultSetMetaData获取结果集的列数
            int column = metaData.getColumnCount();
            if (result.next()) {
                Departments departments = new Departments();
                for (int i = 0; i < column; i++) {
                    //通过ResultSet：获取每列的列值
                    Object columnValue = result.getObject(i + 1);

                    //通过ResultSetMetaData: getColumnName(): 获取列名----不推荐使用
                    //String columnName = metaData.getColumnName(i + 1);

                    //通过ResultSetMetaData: getColumnLabel(): 获取别名
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //通过反射：给departments对象指定的columnName属性，赋值为columnValue
                    Field field = Departments.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(departments, columnValue);
                }
                return departments;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, ps, result);
        }
        return null;
    }
}

class Departments {
    private int department_id;
    private String department_name;
    private int manager_id;
    private int location_id;

    public Departments() {
    }

    public Departments(int department_id, String department_name, int manager_id, int location_id) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.manager_id = manager_id;
        this.location_id = location_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    @Override
    public String toString() {
        return "Departments{" +
                "department_id=" + department_id +
                ", department_name='" + department_name + '\'' +
                ", manager_id=" + manager_id +
                ", location_id=" + location_id +
                '}';
    }
}
