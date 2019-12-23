package com.lmj.constants;

import com.lmj.annotation.scan.RequestMapping;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 19:25 2019-12-12
 **/
public class CommonConstants {
    /**
     * CLASS 后缀
     */
    public static final String CLASS_SUFFIX = ".class";

    /**
     * requestMapping classNode前缀
     */
    public static final String REQUEST_MAPPING_CLASS_NAME_ALIA = "L" + RequestMapping.class.toString().replaceAll("\\.", "/").replaceAll("interface ", "") + ";";
}