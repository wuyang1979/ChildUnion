package com.qinzi123.controller.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title: LoginFilter
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/manage/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    public static final String SESSION_KEY = "user";
    public static final String LUCK_SESSION_KEY = "luck_draw_user";
    public static final String LOGIN = "/login.html";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Object object = request.getSession().getAttribute(SESSION_KEY);
        if (object == null) response.sendRedirect(LOGIN);
        System.out.println("check auth success...");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
