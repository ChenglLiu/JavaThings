package Transaction;

import JDBCUtils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionTest {
    //通用的增删改操作
    //version 1.0
    public int update(String sql, String... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();

            //预编译sql语句
            ps = conn.prepareStatement(sql);

            //填充占位符
            for (int i=0; i<args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            //执行
            //返回对几条数据的修改
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            JDBCUtils.closeResource(conn, ps);
        }
        //修改失败
        return 0;
    }
}
