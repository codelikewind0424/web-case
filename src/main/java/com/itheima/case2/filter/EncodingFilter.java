package com.itheima.case2.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    对所有POST方法请求参数进行编码
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //如果是POST方法，就对汉字进行编码的操作
        request.setCharacterEncoding("utf-8");
        //处理响应乱码
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request, response);
    }
    public void init(FilterConfig config) throws ServletException {
    }
}
