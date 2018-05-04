package com.lbd.core.tags;

import java.util.Map;

import com.lbd.common.utils.StringUtils;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 19:36
 * @Description 自定义标签的父类。
 */
@SuppressWarnings("unchecked")
public abstract class SuperCustomTag {

    /**
     * 本方法采用多态集成的方式，然后用父类接收，用父类调用子类的 {@link result(...)} 方法。
     *
     * @param params
     * @return
     */
    protected abstract Object result(Map params);


    /**
     * 直接强转报错，需要用Object过度一下
     *
     * @param e
     * @return
     */
    protected Long getLong(Map params, String key) {
        Object i = params.get(key);
        return StringUtils.isBlank(i) ? null : new Long(i.toString());
    }

    protected String getString(Map params, String key) {
        Object i = params.get(key);
        return StringUtils.isBlank(i) ? null : i.toString();
    }

    protected Integer getInt(Map params, String key) {
        Object i = params.get(key);
        return StringUtils.isBlank(i) ? null : Integer.parseInt(i.toString());
    }
}
