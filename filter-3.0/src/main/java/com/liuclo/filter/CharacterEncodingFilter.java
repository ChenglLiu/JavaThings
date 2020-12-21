package com.liuclo.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author liuclo
 * @Desciption //过滤器
 * @Date  2020/12/21
 **/
public class CharacterEncodingFilter implements Filter {
    // web服务器启动时，就初始化了，随时监听过滤对象
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化成功...");
    }

    /*
    Chain:
        过滤中的所有代码，在过滤特定请求前将被执行
        必须让过滤器继续执行 doFilter(req, resp);
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        System.out.println("CharacterEncodingFilter过滤前...");

        // 链
        filterChain.doFilter(servletRequest, servletResponse);      // 请求继续执行，否则将被拦截

        System.out.println("CharacterEncodingFilter过滤后...");
    }

    // web服务器关闭时，过滤器销毁
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁成功！！！");
    }
}
