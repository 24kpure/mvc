package com.lmj.bean.factory;

import com.lmj.context.DefaultApplicationContext;
import com.lmj.exception.BeansException;


/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:47 2019-08-07
 **/
public class DefaultBeanFactory implements BeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {
        return DefaultApplicationContext.NAME_BEAN_MAP.get(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return DefaultApplicationContext.NAME_BEAN_MAP.containsKey(beanName);
    }

    @Override
    public <T> T getBeanInstance(String beanName, Class<T> cl) {
        return (T) DefaultApplicationContext.NAME_BEAN_MAP.get(beanName);
    }
}

