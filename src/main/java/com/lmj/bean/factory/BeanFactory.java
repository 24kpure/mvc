package com.lmj.bean.factory;

import com.lmj.exception.BeansException;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:06 2019-08-07
 **/
public interface BeanFactory {

    Object getBean(String var1) throws BeansException;

    <T> T getBean(Class<T> var1);

    boolean containsBean(String var1);


}
