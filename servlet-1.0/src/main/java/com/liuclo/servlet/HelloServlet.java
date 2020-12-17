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
 * @Desciption  first Servlet program & share data
 * @Date  2020/12/16
 **/

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入doGet方法...");
        resp.setCharacterEncoding("GBk");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello, Servlet!! 我来了");

        //放入共享数据到上下文中
        ServletContext context = this.getServletContext();
        context.setAttribute("name", "TORKING");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
