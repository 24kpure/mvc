package com.lmj.annotation.scan;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lmj.annotation.component.Component;
import com.lmj.bean.SingletonMappingBean;
import com.lmj.constants.StringUtils;

import com.lmj.exception.BeansException;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.lmj.constants.CommonConstants.CLASS_SUFFIX;
import static com.lmj.constants.ScanConstant.CLASSLOADER;

/**
 * @author lmj
 * @date 2019/6/22 22:07
 */
@Slf4j
public class ClassPathBeanDefinitionScanner {

    public Map<String, SingletonMappingBean> doScan(String... basePackages) {
        Map<String, SingletonMappingBean> resultMap = Maps.newHashMap();
        for (String basePackage : basePackages) {
            try {
                Set<String> scanClassFileSet = findCandidateComponents(basePackage);
                resultMap.putAll(doMatchClassByFilePath(scanClassFileSet));
            } catch (Exception e) {
                log.error("扫描包下class出错:", e);
            }
        }
        return resultMap;
    }

    /**
     * 获取扫描class
     *
     * @param basePackage
     * @return
     * @throws Exception
     */
    private Set<String> findCandidateComponents(String basePackage) throws Exception {
        Set<String> result = new HashSet<>();
        log.info("扫描包名为：" + basePackage);

        String packageSearchPath =
                basePackage.replaceAll("\\.", File.separator);


        Enumeration<URL> resourceUrls = (CLASSLOADER != null ? CLASSLOADER.getResources(packageSearchPath) : ClassLoader.getSystemResources(packageSearchPath));

        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            listFileList(result, new File(url.getFile()));
        }
        return result;
    }

    /**
     * 递归查询下游class文件
     *
     * @param fileUrlList
     * @param baseUrl
     */
    private void listFileList(Set<String> fileUrlList, File baseUrl) {
        //判断文件是否可访问
        if (!baseUrl.exists() || !baseUrl.canRead()) {
            log.warn("目标目录不可访问：" + baseUrl.getAbsolutePath());
            return;
        }
        if (!baseUrl.isDirectory() && StringUtils.endsWith(baseUrl.getAbsolutePath(), CLASS_SUFFIX)) {
            fileUrlList.add(baseUrl.getAbsolutePath());
        }
        File[] sonFileList = baseUrl.listFiles();
        for (File file : sonFileList) {
            //子文件为目录 递归
            if (file.isDirectory()) {
                listFileList(fileUrlList, file);
            }
            if (StringUtils.endsWith(file.getAbsolutePath(), ".class")) {
                fileUrlList.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * 扫描注解
     *
     * @param fileList 文件列表
     * @return
     * @throws Exception
     */
    public Map<String, SingletonMappingBean> doMatchClassByFilePath(Set<String> fileList) throws Exception {
        Map<String, SingletonMappingBean> map = new HashMap<>();
        try {
            for (String f : fileList) {
                File file = new File(f);
                ClassReader reader = new ClassReader(new FileInputStream(file));
                ClassNode classNode = new ClassNode();
                reader.accept(classNode, 0);
                List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
                if (visibleAnnotations == null) {
                    continue;
                }
                Map<String, AnnotationEntity> annotationMap = new HashMap<>();
                visibleAnnotations.forEach(e -> {
                    AnnotationScanner.getAllAnnotation(e, annotationMap);
                });
                List<AnnotationEntity> anList = Lists.newArrayList(annotationMap.values());
                if (anList.stream().anyMatch(e -> Component.class.equals(e.getCl()))) {
                    map.put(classNode.name, SingletonMappingBean.getInstance(classNode.name, classNode, anList));
                }
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            log.error("scan bean error:", ex);
            throw new BeansException("scan bean error", ex);
        }
        return map;
    }


}
