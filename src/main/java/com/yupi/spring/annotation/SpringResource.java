package com.yupi.spring.annotation;

import com.sun.xml.internal.bind.v2.model.core.EnumConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述：依赖注入
 *
 * @author Yupi Li
 * @date 2018/8/28 22:33
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringResource {
}
