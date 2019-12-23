package com.lmj.context;

import com.google.common.collect.Maps;
import com.lmj.bean.SingletonMappingBean;

import java.util.Map;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 19:34 2019-12-12
 **/
public class DefaultApplicationContext implements ApplicationContext {

    /**
     * url-bean映射
     */
    public final static Map<String, SingletonMappingBean> URL_BEAN_MAP = Maps.newConcurrentMap();

    /**
     * name-bean映射
     */
    public final static Map<String, SingletonMappingBean> NAME_BEAN_MAP = Maps.newConcurrentMap();

    /**
     * class-bean映射
     */
    public final static Map<Class, SingletonMappingBean> CLASS_BEAN_MAP = Maps.newConcurrentMap();

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }
}