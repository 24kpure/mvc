package com.lmj.bean;

import com.lmj.annotation.scan.AnnotationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.objectweb.asm.tree.ClassNode;

import java.util.List;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 10:31 2019-08-07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseBean {

    private String className;

    private ClassNode classNode;

    private Class cl;

    private List<AnnotationEntity> annotationEntities;

}