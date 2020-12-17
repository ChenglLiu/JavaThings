package com.liuclo.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author liuclo
 * @Desciption //获取初始化参数 从 web.xml文件
 * @Date  2020/12/17
 **/
public class ParameterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();

        //从上下文获取共享数据
        String n = (String) context.getAttribute("name");

        String name = context.getInitParameter("name");

        PrintWriter writer = resp.getWriter();

        writer.println(n);
        writer.println(name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
