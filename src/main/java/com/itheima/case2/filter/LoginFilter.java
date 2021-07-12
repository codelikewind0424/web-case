package com.itheima.case2.filter;


import com.itheima.case2.utils.AppJwtUtil;
import com.itheima.case2.utils.BaseController;
import com.itheima.case2.vo.Result;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//拦截左右
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    // 白名单  在名单上的路径不需要拦截
    // 下面的 / 表示默认访问的页面 index.jsp index.html
    List<String> urlList = Arrays.asList(
            "/",
            "/loginServlet",
            "*.html",
            "*.js",
            "*.css",
            "*.png",
            "*.jpg",
            "/element-ui/*"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("登录过滤器 已经启动啦");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 设置允许跨域
        setCros(response);

        // 查看是否是需要放行的路径,如果是,则放行,如果不是检查用户tocken
        boolean isExclude = isFilterExcludeRequest(request);
        if (isExclude){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        // 检查用户token
        String token = request.getHeader("Authorization");
        if(token == null || "".equals(token)){
            BaseController.printResult(response,new Result(false,"token不存在！"));
            return;
        }
        // 解析token
        try {
            Claims claims = AppJwtUtil.checkToken(token);
            Integer id = (Integer)claims.get("userId");
            System.out.println("用户id:" + id);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        } catch (Exception e) {
            BaseController.printResult(response,new Result(false,"token过期或无效！"));
        }
    }

    private void setCros(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        //自定义Authorization请求头用于发送token
        response.setHeader("Access-Control-Allow-Headers", "content-type,Authorization");
    }

    /**
     * 判断是否是 过滤器直接放行的请求
     * <br/>主要用于静态资源的放行
     * @return
     */
    private boolean isFilterExcludeRequest(HttpServletRequest request) {
        if(null != urlList && urlList.size() > 0) {
            String url = request.getRequestURI();
            for (String ecludedUrl : urlList) {
                if (ecludedUrl.startsWith("*.")) {
                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
                    if(url.endsWith(ecludedUrl.substring(1))){
                        return true;
                    }
                } else if (ecludedUrl.endsWith("/*")) {
                    if(!ecludedUrl.startsWith("/")) {
                        // 前缀匹配，必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
                    String prffixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
                    if(url.startsWith(prffixStr)) {
                        return true;
                    }
                } else {
                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
                    if(!ecludedUrl.startsWith("/")) {
                        // 全路径匹配，也必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    String targetUrl = request.getContextPath() + ecludedUrl;
                    if(url.equals(targetUrl)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
