package com.lbd.filesystem.common.annotation;

import java.lang.annotation.*;

/**
 * 
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月11日 下午13:56:10
 * @Description: 自定义注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {

	String description()  default "";
}
