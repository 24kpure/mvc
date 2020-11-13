package com.lmj.annotation.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 5:39 下午 2020/1/23
 **/
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String value();

    boolean require() default true;
}
