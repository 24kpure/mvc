package com.lmj.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 15:24 2019-08-07
 **/
@Slf4j
public class RegisBeanUtils {

    public static void regisBean(Map<String, BaseBean> map) {
        List<BaseBean> beanList = map.values().stream().collect(Collectors.toList());
        for (BaseBean baseBean : beanList) {
            try {
                SingletonMappingBean singletonBean = SingletonMappingBean.getInstance(baseBean);

                //甚至instance
                Object o = Class.forName(baseBean.getClassName().replaceAll("/", "\\.")).newInstance();
                singletonBean.setSingletonInstance(o);

                //方法初始化
                baseBean.getClassNode().methods.forEach(e->{



                });

            } catch (Exception e) {
                log.error("初始化bean失败：{}", baseBean.getClassName(), e);
            }
        }
    }
}