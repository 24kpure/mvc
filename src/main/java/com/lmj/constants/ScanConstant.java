package com.lmj.constants;


import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.net.URL;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 10:32 2019-11-04
 **/
public final class ScanConstant {

    /**
     * requestMapping对应的描述
     */
    public static final String REQUEST_MAPPING_DESC = "Lcom/lmj/annotation/scan/RequestMapping;";

    /**
     * 类加载器
     */
    public static final ClassLoader CLASSLOADER = ScanConstant.class.getClassLoader();

    public static ClassReader getClassReader(final String className) throws IOException {
        URL url = CLASSLOADER.getResource(className.replace('.', '/') + ".class");
        return new ClassReader(url != null ? url.openStream() : null);
    }


}