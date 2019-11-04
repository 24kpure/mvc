package com.lmj.bean;

import com.google.common.collect.Maps;
import com.lmj.annotation.scan.AnnotationEntity;
import com.lmj.constants.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:20 2019-08-07
 **/
@Data
@Slf4j
@NoArgsConstructor
public class SingletonMappingBean<T> extends BaseBean {

    /**
     * 单例常量
     */
    private T singletonInstance;

    /**
     * 匹配方法
     */
    private Map<String, Method> mappingMethod = Maps.newHashMap();


    public static SingletonMappingBean getInstance(String className, ClassNode classNode, List<AnnotationEntity> annotationNodes) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        SingletonMappingBean bean = new SingletonMappingBean();
        bean.setClassName(className);
        bean.setClassNode(classNode);
        bean.setAnnotationEntities(annotationNodes);
        bean.setSingletonInstance(Class.forName(StringUtils.asmDesToClassName(className)).newInstance());
        return bean;
    }
}