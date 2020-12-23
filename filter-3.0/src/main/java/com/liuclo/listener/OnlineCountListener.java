package com.liuclo.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author liuclo
 * @Desciption //统计在线人数
 * @Date  2020/12/22
 **/
public class OnlineCountListener implements HttpSessionListener {
    // 一旦创建就会被监听到
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ServletContext context = httpSessionEvent.getSession().getServletContext();

        Integer count = (Integer) context.getAttribute("count");
        if (null == count) {
            count = new Integer(1);
        } else {
            count = new Integer(count + 1);
        }
        context.setAttribute("count", count);
    }

    // 销毁
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext context = httpSessionEvent.getSession().getServletContext();

        // 手动销毁session
        httpSessionEvent.getSession().invalidate();

        Integer count = (Integer) context.getAttribute("count");

        if (count > 1) {
            count--;
        } else {
            count = 0;
        }
        context.setAttribute("count", count);
    }
}
