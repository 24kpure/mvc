package com.lmj.annotation.scan;

import com.google.common.collect.Lists;
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

    private static Map<String, SingletonMappingBean> singletonMappingBeanHashMap = new HashMap<>(256);

    public Map<String, SingletonMappingBean> doScan(String... basePackages) {
        //todo unHandle exception
        if (!singletonMappingBeanHashMap.isEmpty()) {
            return singletonMappingBeanHashMap;
        }

        synchronized (this) {
            if (!singletonMappingBeanHashMap.isEmpty()) {
                return singletonMappingBeanHashMap;
            }

            //scan by package
            for (String basePackage : basePackages) {
                try {
                    Set<String> scanClassFileSet = findCandidateComponents(basePackage);
                    singletonMappingBeanHashMap.putAll(doMatchClassByFilePath(scanClassFileSet));
                } catch (Exception e) {
                    log.error("scan class failure:", e);
                }
            }
        }

        return singletonMappingBeanHashMap;
    }

    /**
     * scan package
     *
     * @param basePackage basePackage
     * @return set<string>
     * @throws Exception
     */
    private Set<String> findCandidateComponents(String basePackage) throws Exception {
        Set<String> result = new HashSet<>(256);
        log.info("scan package：" + basePackage);

        String packageSearchPath =
                basePackage.replaceAll("\\.", File.separator);

        Enumeration<URL> resourceUrls = CLASSLOADER != null ?
                CLASSLOADER.getResources(packageSearchPath) : ClassLoader.getSystemResources(packageSearchPath);

        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            listFileList(result, new File(url.getFile()));
        }

        return result;
    }

    /**
     * scan direction or class
     *
     * @param fileUrlList fileUrlList
     * @param baseUrl     baseUrl
     */
    private void listFileList(Set<String> fileUrlList, File baseUrl) {
        //判断文件是否可访问
        if (!baseUrl.exists() || !baseUrl.canRead()) {
            log.warn("url is not exist or can't read：" + baseUrl.getAbsolutePath());
            return;
        }
        if (!baseUrl.isDirectory() && StringUtils.endsWith(baseUrl.getAbsolutePath(), CLASS_SUFFIX)) {
            fileUrlList.add(baseUrl.getAbsolutePath());
        }
        File[] sonFileList = baseUrl.listFiles();
        if (null == sonFileList || sonFileList.length <= 0) {
            return;
        }

        for (File file : sonFileList) {
            //direction
            if (file.isDirectory()) {
                listFileList(fileUrlList, file);
            }
            if (StringUtils.endsWith(file.getAbsolutePath(), ".class")) {
                fileUrlList.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * scan annotation
     *
     * @param fileList fileList
     * @return
     * @throws Exception
     */
    public Map<String, SingletonMappingBean> doMatchClassByFilePath(Set<String> fileList) throws Exception {
        Map<String, SingletonMappingBean> map = new HashMap<>();
        try {
            for (String filePath : fileList) {
                File file = new File(filePath);
                ClassReader reader = new ClassReader(new FileInputStream(file));
                ClassNode classNode = new ClassNode();
                reader.accept(classNode, 0);
                List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
                if (visibleAnnotations == null) {
                    continue;
                }

                Map<String, AnnotationEntity> annotationMap = new HashMap<>();
                visibleAnnotations.forEach(e -> AnnotationScanner.getAllAnnotation(e, annotationMap));
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
