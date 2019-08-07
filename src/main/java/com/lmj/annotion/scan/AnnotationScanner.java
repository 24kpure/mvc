package com.lmj.annotion.scan;

import com.lmj.annotion.component.Controller;
import com.lmj.constants.CollectionUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 09:56 2019-08-07
 **/
public class AnnotationScanner {
    /**
     * 获取注解和其所有注解
     *
     * @return
     */
    protected Map<String,AnnotationNode> getAllAnnotation(AnnotationNode node, Map<String,AnnotationNode> annotationMap) {
        try {
            String annotationName = node.desc.substring(1).replace(";", "").replace("/", ".");
            ClassReader reader = new ClassReader(annotationName);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);
            if (!annotationMap.containsKey(node.desc)) {
                annotationMap.put(node.desc,node);
            }
            if (CollectionUtils.isNotEmpty(classNode.visibleAnnotations)) {
                classNode.visibleAnnotations.stream().filter(e -> !annotationMap.containsKey(e.desc)).forEach(e -> {
                    getAllAnnotation(e, annotationMap);
                });
            }
        } catch (Exception e) {

        }
        return annotationMap;
    }
}