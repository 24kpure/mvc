package com.lmj.annotion.scan;

import com.google.common.collect.Maps;
import com.lmj.bean.BaseBean;
import com.lmj.bean.RegisBeanUtils;
import com.lmj.constants.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.*;


/**
 * @author lmj
 * @date 2019/6/22 22:07
 */
@Slf4j
public class ClassPathBeanDefinitionScanner {

    public static final String CLASS_SUFFIX = ".class";


    public Map<String, BaseBean> doScan(String... basePackages) {
        Map resultMap = Maps.newHashMap();
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

        //获取扫描路径下目录或文件
        ClassLoader cl = basePackage.getClass().getClassLoader();
        String packageSearchPath =
                basePackage.replaceAll("\\.", "/");
        Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(packageSearchPath) : ClassLoader.getSystemResources(packageSearchPath));

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
    public Map<String, BaseBean> doMatchClassByFilePath(Set<String> fileList) throws Exception {
        Map<String, BaseBean> map = new HashMap<>();
        for (String f : fileList) {
            File file = new File(f);
            ClassReader reader = new ClassReader(new FileInputStream(file));
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);
            List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
            if (visibleAnnotations == null) {
                continue;
            }
            Map<String, AnnotationNode> annotationMap = new HashMap<>();
            visibleAnnotations.forEach(e -> {
                new AnnotationScanner().getAllAnnotation(e, annotationMap);
            });
            List<AnnotationNode> anList = new ArrayList<>(annotationMap.values());
            if (!anList.stream().anyMatch(e -> StringUtils.equals(e.desc, "Lcom/lmj/annotion/component/Component;"))) {
                continue;
            }
            map.put(classNode.name, BaseBean.getInstance(classNode.name, classNode, anList));
        }
        return map;
    }


    public static void main(String[] args) throws Exception {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner();
        RegisBeanUtils.regisBean(scanner.doScan("com.lmj.controller"));
    }
}
