package com.lmj.annotion.component;

/**
 * @author lmj
 * @date 2019/6/19 21:58
 */


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.*;

/**
 * 基础容器组件
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
