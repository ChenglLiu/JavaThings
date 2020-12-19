package com.liuclo.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("GBK");
        resp.setCharacterEncoding("GBK");

        PrintWriter out = resp.getWriter();

        //Cookie
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            out.println("感谢您曾经来过....");
            for (Cookie cookie : cookies) {
                // 获取cookie的名（键）
                if ("time".equals(cookie.getName())) {
                    // 获取cookie的值（值）
                    long time = Long.parseLong(cookie.getValue());
                    Date date = new Date(time);

                    out.print("您上次访问时间是: ");
                    out.println(date.toLocaleString());
                }
            }
        } else {
            out.println("您第一次访问本站...");
        }

        // 添加访问时间
        resp.addCookie(new Cookie("time", System.currentTimeMillis() + ""));

        out.println("再见啦，欢迎您下次再来！！！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
