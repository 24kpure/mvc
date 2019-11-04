package com.lmj.bean;

import com.google.common.collect.Maps;
import com.lmj.annotation.scan.RequestMapping;
import com.lmj.constants.CollectionUtils;
import com.lmj.exception.BeansException;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:24 2019-08-07
 **/
@Slf4j
public class RegistryBeanUtils {

    public static final String REQUEST_MAPPING_CLASS_NAME_ALIA = "L" + RequestMapping.class.toString().replaceAll("\\.", "/").replaceAll("interface ", "") + ";";

    public static void registryBean(Map<String, SingletonMappingBean> map) {
        List<SingletonMappingBean> beanList = map.values().stream().collect(Collectors.toList());
        for (SingletonMappingBean baseBean : beanList) {
            try {

                Map<String, Method> methodMap = getMethodMapping(baseBean);


            } catch (Exception e) {
                log.error("初始化bean失败：{}", baseBean.getClassName(), e);
            }
        }
    }

    private static Map<String, Method> getMethodMapping(SingletonMappingBean singletonBean) {
        Map<String, Method> map = Maps.newHashMap();
        //方法初始化
        singletonBean.getClassNode().methods.stream().filter(e -> CollectionUtils.isNotEmpty(e.visibleAnnotations)).forEach(e -> {
            //
            List<AnnotationNode> annotationNodeList = e.visibleAnnotations.stream().filter(v -> REQUEST_MAPPING_CLASS_NAME_ALIA.equals(v.desc)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(annotationNodeList)) {
                return;
            }
            if (annotationNodeList.size() > 1) {
                throw new BeansException("mapping method is not unique");
            }
            AnnotationNode mappingAnnotation=annotationNodeList.get(0);
        });
        return map;
    }
}