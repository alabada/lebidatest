package com.lbd.filesystem.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by luozhanghua on 2016/7/12.
 * 跨域过滤器，解决跨域问题
 */
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        HttpServletRequest httpReq = (HttpServletRequest) req;
        httpResp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        httpResp.setHeader("Access-Control-Allow-Origin", "*");
        if (httpReq.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpResp.setHeader("Access-Control-Allow-Headers",
                    httpReq.getHeader("Access-Control-Request-Headers"));
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
