package com.lbd.core.ueditor;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 描述
 *
 * @author zhiheng.li
 * @since 2017/3/24
 */
public class CommonsMultiparResolver extends CommonsMultipartResolver {

    @Override
    public boolean isMultipart(javax.servlet.http.HttpServletRequest request) {
        String uri = request.getRequestURI();
        System.out.println(uri);
        //过滤使用百度UEditor的URI
        if (uri.indexOf("ueditor") > 0) {     //此处拦截路径即为上面编写的controller路径
            System.out.println("commonsMultipartResolver 放行");
            return false;
        }
        System.out.println("commonsMultipartResolver 拦截");
        return super.isMultipart(request);
    }
}

