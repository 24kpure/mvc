package com.lmj.annotion.component;

import java.lang.annotation.*;

/**
 * @author lmj
 * @date 2019/6/19 21:59
 * 此处只关注mvc  所以只有控制器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

    String name() default "";
}
