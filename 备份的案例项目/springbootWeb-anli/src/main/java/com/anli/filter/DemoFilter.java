package com.anli.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/7/9 14:23
 */
//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {

    @Override//初始化方法，调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init 初始化方法执行了");
    }

    @Override//拦截到请求之后调用，调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到了请求...放行前的逻辑");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("拦截到了请求...放行后的逻辑");
    }

    @Override//销毁的方法，只调用一次
    public void destroy() {
        System.out.println("destroy 销毁的方法执行了");
    }
}
