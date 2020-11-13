package com.lmj.bean;

import com.lmj.annotation.RequestMethod;
import com.lmj.annotation.scan.AnnotationEntity;
import com.lmj.constants.CollectionUtils;
import com.lmj.constants.DataType;
import com.lmj.constants.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:20 2019-08-07
 **/
@Getter
@Setter
@NoArgsConstructor
public class SingletonMappingBean<T> extends BaseBean {

    /**
     * 单例常量
     */
    private T singletonInstance;

    /**
     * 匹配方法
     */
    private Map<String, MappingMethod> mappingMethod = new HashMap<>();


    @Data
    @AllArgsConstructor
    public static class MappingMethod {
        private Method invokeMethod;

        private Set<RequestMethod> requestMethod;

        private DataType responseDataType;

        private DataType requestDataType;

        private Set<String> pathNameSet;
    }

    public MappingMethod getMappingMethod(String url) {
        return this.mappingMethod.get(url);
    }


    public void addMappingMethod(String url, Method invokeMethod, DataType requestDataType, DataType responseDataType, Set<RequestMethod> requestMethod, Set<String> pathNameSet) {
        if (CollectionUtils.isEmpty(requestMethod)) {
            requestMethod = Arrays.stream(RequestMethod.values()).collect(Collectors.toSet());
        }

        this.mappingMethod.put(url, new MappingMethod(invokeMethod, requestMethod, requestDataType, responseDataType, pathNameSet));
    }

    public static SingletonMappingBean getInstance(String className, ClassNode classNode, List<AnnotationEntity> annotationNodes) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String clName = StringUtils.asmDesToClassName(className);
        SingletonMappingBean bean = new SingletonMappingBean();
        bean.setClassName(clName);
        bean.setClassNode(classNode);
        bean.setAnnotationEntities(annotationNodes);
        bean.setSingletonInstance(Class.forName(clName).newInstance());
        bean.setCl(bean.getSingletonInstance().getClass());

        return bean;
    }
}