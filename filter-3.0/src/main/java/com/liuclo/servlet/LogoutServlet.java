package com.liuclo.servlet;

import com.liuclo.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liuclo
 * @Desciption //注销登录
 * @Date  2020/12/22
 **/
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object obj = req.getSession().getAttribute(Constant.USER_SESSION);

        if (null != obj) {
            req.getSession().removeAttribute(Constant.USER_SESSION);
            resp.sendRedirect("/liuclo3/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
