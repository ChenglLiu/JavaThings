package com.liuclo.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liuclo
 * @Desciption //请求转发
 * @Date  2020/12/17
 **/

public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("测试ing...\nstep into DispatcherServlet...");
        ServletContext context = this.getServletContext();

        //转发的请求路径
        RequestDispatcher dispatcher = context.getRequestDispatcher("/parameter");

        //实现请求转发
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
