package com.lmj.bean;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lmj.annotation.RequestMethod;
import com.lmj.constants.CollectionUtils;
import com.lmj.constants.StringUtils;
import com.lmj.context.DefaultApplicationContext;
import com.lmj.exception.BeansException;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lmj.constants.ScanConstant.REQUEST_MAPPING_DESC;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:24 2019-08-07
 **/
@Slf4j
public class RegistryBeanUtils {

    public static void registryBean(Map<String, SingletonMappingBean> map) {
        List<SingletonMappingBean> beanList = map.values().stream().collect(Collectors.toList());
        for (SingletonMappingBean baseBean : beanList) {
            //baseBean.getMap
            initBeanProperties(baseBean);
            baseBean.getMappingMethod().forEach((k, v) -> {
                if (DefaultApplicationContext.URL_BEAN_MAP.containsKey(k.toString())) {
                    throw new BeansException("路径重复：" + k + "in " + DefaultApplicationContext.URL_BEAN_MAP.get(k.toString()) + " and " + baseBean.getClassName());
                }
                DefaultApplicationContext.URL_BEAN_MAP.putIfAbsent(k.toString(), baseBean);
                DefaultApplicationContext.NAME_BEAN_MAP.putIfAbsent(baseBean.getClassName(), baseBean);
                DefaultApplicationContext.CLASS_BEAN_MAP.putIfAbsent(baseBean.getCl(), baseBean);
            });
        }
    }

    private static void initBeanProperties(SingletonMappingBean singletonBean) {
        List<AnnotationNode> annotationNodeList = singletonBean.getClassNode().visibleAnnotations
                .stream()
                .filter(f -> f.desc.equals(REQUEST_MAPPING_DESC))
                .collect(Collectors.toList());
        String classBaseUrl = CollectionUtils.isEmpty(annotationNodeList) ? "" : getAnnotationParamMap(annotationNodeList.get(0).values).get("value").toString();

        //方法初始化
        singletonBean.getClassNode().methods.stream().filter(e -> CollectionUtils.isNotEmpty(e.visibleAnnotations)).forEach(e -> {
            //
            List<AnnotationNode> methodAnnotationNodeList = e.visibleAnnotations.stream().filter(f -> f.desc.equals(REQUEST_MAPPING_DESC)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(methodAnnotationNodeList)) {
                return;
            }
            if (methodAnnotationNodeList.size() > 1) {
                throw new BeansException("mapping method is not unique");
            }
            Map<String, Object> classParamMap = getAnnotationParamMap(methodAnnotationNodeList.get(0).values);
            String methodUrl = classParamMap.get("value") == null ? "" : classParamMap.get("value").toString();
            List<String[]> methods = (List<String[]>) classParamMap.get("method");

            Set<RequestMethod> requestMethods = methods == null ? Sets.newHashSet() :
                    methods.stream().filter(m -> "Lcom/lmj/annotation/RequestMethod;".equals(m[0])).map(m ->
                            RequestMethod.valueOf(m[1])).collect(Collectors.toSet());

            try {
                String mappingUrl = classBaseUrl + (StringUtils.startsWith(methodUrl, "/") ? methodUrl : "/" + methodUrl);
                mappingUrl.replace("//", "/");
                singletonBean.addMappingMethod(mappingUrl, singletonBean.getCl().getDeclaredMethod(e.name), requestMethods);
            } catch (NoSuchMethodException ex) {
                throw new BeansException("方法初始化失败");
            }
        });
    }

    private static Map<String, Object> getAnnotationParamMap(List<Object> values) {
        Map<String, Object> paramMaps = Maps.newHashMap();
        for (int i = 0; i < values.size(); i = i + 2) {
            paramMaps.put(values.get(i).toString(), values.get(i + 1));
        }
        return paramMaps;
    }
}