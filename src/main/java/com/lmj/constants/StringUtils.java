package com.lmj.constants;

import java.io.File;

/**
 * @author lmj
 * @date 2019/6/23 13:24
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * asm description translate className
     *
     * @param asmDesc asm description
     * @return
     */
    public static String asmDesToClassName(String asmDesc) {
        if (StringUtils.startsWith(asmDesc, "L")) {
            asmDesc = asmDesc.substring(1);
        }
        return asmDesc.replace(";", "").replace(File.separator, ".");
    }

    /**
     * asm description compare sourceClassName
     *
     * @param desc
     * @param cl
     * @return
     */
    public static boolean asmDesEqualClass(String desc, Class cl) {
        return equals(asmDesToClassName(desc), cl.getName());
    }
}
