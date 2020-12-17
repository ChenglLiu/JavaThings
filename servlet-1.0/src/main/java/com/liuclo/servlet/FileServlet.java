package com.liuclo.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author liuclo
 * @Desciption //下载文件
 * @Date  2020/12/17
 **/
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取下载文件路径
        String realPath = this.getServletContext().getRealPath("\\arrow.png");
        realPath = "D:\\Code\\workspace_IDEA\\JavaWeb-02\\servlet-1.0\\src\\main\\resources\\arrow.png";
        System.out.println(realPath);

        //获取文件名
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1);

        //设置浏览器支持下载
        //URLEncoder.encode(fileName, "UTF-8");         如果文件名时中文，设置编码格式
        resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        //获取下载输入流
        FileInputStream fis = new FileInputStream(realPath);

        //创建缓冲区
        byte[] buffer = new byte[1024];
        int len = 0;

        //获取输出流对象
        ServletOutputStream os = resp.getOutputStream();

        //将文件写入缓冲区
        while ((len = fis.read(buffer)) != -1) {
            //写出到客户端
            os.write(buffer, 0, len);
        }

        //关闭流对象
        os.close();
        fis.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
