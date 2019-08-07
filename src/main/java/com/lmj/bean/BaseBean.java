package com.lmj.bean;

import lombok.Data;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.util.List;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 10:31 2019-08-07
 **/
@Data
public class BaseBean {

    private String className;

    private ClassNode classNode;

    private List<AnnotationNode> annotationNodes;

    public static BaseBean getInstance(String className, ClassNode classNode, List<AnnotationNode> annotationNodes) {
        BaseBean bean = new BaseBean();
        bean.setClassName(className);
        bean.setClassNode(classNode);
        bean.setAnnotationNodes(annotationNodes);
        return bean;
    }
}