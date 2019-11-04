package com.lmj.annotation.scan;

import com.lmj.constants.CollectionUtils;
import com.lmj.constants.ScanConstant;
import com.lmj.constants.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.Map;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 09:56 2019-08-07
 **/
public class AnnotationScanner {
    /**
     * 获取注解和其所有注解
     *
     * @param node  asmNode
     * @param annotationMap
     * @return
     */
    protected static Map<String, AnnotationEntity> getAllAnnotation(AnnotationNode node, Map<String, AnnotationEntity> annotationMap) {
        try {
            String annotationName = StringUtils.asmDesToClassName(node.desc);
            ClassReader reader = ScanConstant.getClassReader(annotationName);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);
            if (!annotationMap.containsKey(node.desc)) {
                annotationMap.put(node.desc, AnnotationEntity.getInstance(node));
            }
            if (CollectionUtils.isNotEmpty(classNode.visibleAnnotations)) {
                classNode.visibleAnnotations.stream().filter(e -> !annotationMap.containsKey(e.desc)).forEach(e -> {
                    getAllAnnotation(e, annotationMap);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annotationMap;
    }
}