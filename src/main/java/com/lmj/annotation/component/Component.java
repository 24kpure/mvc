package com.lmj.annotation.component;


import java.lang.annotation.*;

/**
 * @author lmj
 * @date 2019/6/19 21:58
 * 基本扫描单元
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
