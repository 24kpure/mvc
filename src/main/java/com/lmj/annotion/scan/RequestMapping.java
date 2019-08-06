package com.lmj.annotion.scan;

import com.lmj.annotion.RequestMethod;

import java.lang.annotation.*;

/**
 * @author lmj
 * @date 2019/6/17 23:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";

    RequestMethod[] method() default {};
}
