package com.lmj.bean.factory;

import com.lmj.bean.SingletonMappingBean;
import com.lmj.exception.BeansException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:47 2019-08-07
 **/
public class DefaultBeanFactory implements BeanFactory {

    private final Map<String, String> singletonBeanMap = new ConcurrentHashMap(64);

    private final Map<String, Class> singletonInstanceMap = new ConcurrentHashMap(64);


    @Override
    public Object getBean(String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String beanName) {
        return singletonBeanMap.containsKey(beanName);
    }

    @Override
    public boolean putBean(SingletonMappingBean bean) {
        return singletonBeanMap.put(bean.getClassName(), bean.getClassName()) != null;
    }

    @Override
    public <T> T getBeanInstance(String beanName, Class<T> cl) {
        return (T)singletonInstanceMap.get(beanName);
    }
}

