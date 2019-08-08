package com.lmj.bean.factory;

import com.lmj.bean.SingletonMappingBean;
import com.lmj.exception.BeansException;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:06 2019-08-07
 **/
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String beanName);

    boolean putBean(SingletonMappingBean bean);

    <T> T getBeanInstance(String beanName, Class<T> cl);

}
