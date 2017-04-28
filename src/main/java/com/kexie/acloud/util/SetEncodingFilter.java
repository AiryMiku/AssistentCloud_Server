package com.kexie.acloud.util;

/**
 * Created by zojian on 2017/4/27.
 */
import javax.servlet.*;
import java.io.IOException;
/**
 * 请求中中文字符串过滤类
 */
public class SetEncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
    }
    public void destroy() {
    }
}
