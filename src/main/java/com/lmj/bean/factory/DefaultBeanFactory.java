package com.lmj.bean.factory;

import com.lmj.exception.BeansException;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:47 2019-08-07
 **/
public class DefaultBeanFactory implements BeanFactory{
    @Override
    public Object getBean(String var1) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> var1) {
        return null;
    }

    @Override
    public boolean containsBean(String var1) {
        return false;
    }
}