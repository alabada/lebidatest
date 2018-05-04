package com.lbd.core.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.lbd.common.model.UUser;
import com.lbd.common.utils.LoggerUtils;
import com.lbd.core.shiro.token.manager.TokenManager;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 23:49
 * @Description 判断登录
 */
public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;

    /**
     * 表示是否允许访问mappedValue就是[urls]配置中拦截器参数部分
     * 如果允许访问放回true，否则false
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

        UUser token = TokenManager.getToken();

        if (null != token || isLoginRequest(request, response)) { // && isEnabled()
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isAjax(request)) {// ajax请求
            Map<String, String> resultMap = new HashMap<String, String>();
            LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
            resultMap.put("login_status", "300");
            resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
            ShiroFilterUtils.out(response, resultMap);
        }
        return Boolean.FALSE;

    }

    /**
     * 表示当访问拒绝时是否已经处理了
     * 如果返回true表示需要继续处理
     * 如果放回false表示该拦截器实例已经处理了
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        // 将当前请求保存起来并重定向到登录页面
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE;
    }


}
