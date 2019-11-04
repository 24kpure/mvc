package com.lmj.constants;

import java.io.File;
import java.util.Collection;

/**
 * @author lmj
 * @date 2019/6/23 13:24
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[0]);
    }

    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        }
        if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * asm 描述转className
     *
     * @param asmDesc asm 描述
     * @return
     */
    public static String asmDesToClassName(String asmDesc) {
        if (StringUtils.startsWith(asmDesc, "L")) {
            asmDesc = asmDesc.substring(1);
        }
        return asmDesc.replace(";", "").replace(File.separator, ".");
    }
}
