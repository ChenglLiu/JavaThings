package com.liuclo.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        //得到session
        HttpSession session = req.getSession();

        //存入数据
        session.setAttribute("name", "PLUS");

        //获取sessionID
        String id = session.getId();

        //判断session
        if (session.isNew()) {
            resp.getWriter().write("您是第一次访问本站，欢迎！！！");
            resp.getWriter().write("您的ID为: " + id);
        } else {
            resp.getWriter().write("欢迎您再次访问本站...");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
