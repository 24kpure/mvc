package com.lmj.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmj.annotation.RequestMethod;
import com.lmj.annotation.scan.ClassPathBeanDefinitionScanner;
import com.lmj.bean.RegistryBeanUtils;
import com.lmj.bean.SingletonMappingBean;
import com.lmj.constants.ResponseDataType;
import com.lmj.context.DefaultApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


/**
 * @author lmj
 * @date 2019/6/19 21:17
 * <p>
 * 实际处理请求类
 */
@Slf4j
public class DispatcherServlet extends BasicServlet {

    private static ObjectMapper jacksonMapper = new ObjectMapper();

    @Override
    protected void doPreRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String url = req.getRequestURI().substring(req.getContextPath().length());
        SingletonMappingBean responseBean = DefaultApplicationContext.URL_BEAN_MAP.get(url);
        if (responseBean == null) {
            throw new ServletException("未找到匹配路径");
        }
        try {
            SingletonMappingBean.MappingMethod mappingMethod = responseBean.getMappingMethod(url);
            if (!mappingMethod.getRequestMethod().contains(RequestMethod.valueOf(req.getMethod()))) {
                throw new ServletException("httpMethod error");
            }

            //invoke method
            responseBean.getMappingMethod(url);
            Method invokeMethod = mappingMethod.getInvokeMethod();
            Object responseContent = invokeMethod.invoke(responseBean.getSingletonInstance());
            if (Void.TYPE.equals(invokeMethod.getAnnotatedReturnType().getType())) {
                log.trace(" void method:{},{}", url, invokeMethod);
                return;
            }
            if (mappingMethod.getResponseDataType() == ResponseDataType.JSON) {
                responseContent = jacksonMapper.writeValueAsString(responseContent);
            }

            //flush content
            writer.print("it is doing " + responseContent);
            writer.flush();
        } catch (Exception ex) {
            log.error("错误：", ex);
        }
    }

    @Override
    protected void scanComponent() {
        String url = "com.lmj.controller";
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner();
        RegistryBeanUtils.registryBean(scanner.doScan(url));
    }
}
