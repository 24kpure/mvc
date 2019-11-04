package com.lmj.annotation.scan;

import com.google.common.collect.Maps;
import com.lmj.constants.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 10:50 2019-11-04
 **/
@Data
@NoArgsConstructor
public class AnnotationEntity {
    /**
     * class
     */
    private Class cl;
    /**
     * className String
     */
    private String className;
    /**
     * 值map
     */
    private Map<String, Object> valueMap;

    /**
     * 根据原始asm annotationNode构造注解实体
     *
     * @param node
     * @return
     * @throws ClassNotFoundException
     */
    public static AnnotationEntity getInstance(AnnotationNode node) throws ClassNotFoundException {
        AnnotationEntity entity = new AnnotationEntity();
        entity.setClassName(StringUtils.asmDesToClassName(node.desc));
        entity.setCl(Class.forName(entity.getClassName()));
        Map<String, Object> valueMap = Maps.newHashMap();
        for (int i = 0; node.values != null && i < node.values.size(); i += 2) {
            valueMap.put(node.values.get(i).toString(), node.values.get(i + 1));
        }
        return entity;
    }
}