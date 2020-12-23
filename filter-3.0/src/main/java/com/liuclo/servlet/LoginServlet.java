package com.liuclo.servlet;

import com.liuclo.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "123456".equals(password)) {     //登录失败
            HttpSession session = req.getSession();
            session.setAttribute(Constant.USER_SESSION, session.getId());
            resp.sendRedirect("/liuclo3/sys/succeed.jsp");
        } else {                                                        // 登录失败
            resp.sendRedirect("/liuclo3/sys/wrong.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
