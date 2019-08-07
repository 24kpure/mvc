package com.lmj.bean;

import com.google.common.collect.Maps;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:20 2019-08-07
 **/
@Data
public class SingletonMappingBean<T> extends BaseBean {

    /**
     * 单例常量
     */
    private T singletonInstance;

    /**
     * 匹配方法
     */
    private Map<String, Method> mappingMethod = Maps.newHashMap();

    public static SingletonMappingBean getInstance(BaseBean baseBean) {
        SingletonMappingBean bean = new SingletonMappingBean();
        bean.setClassName(baseBean.getClassName());
        bean.setClassNode(baseBean.getClassNode());
        bean.setAnnotationNodes(baseBean.getAnnotationNodes());
        return bean;
    }
}