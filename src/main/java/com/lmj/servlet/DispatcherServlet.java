package com.lmj.servlet;




import com.lmj.annotion.scan.ClassPathBeanDefinitionScanner;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author lmj
 * @date 2019/6/19 21:17
 *
 * 实际处理请求类
 *
 */
@Slf4j
public class DispatcherServlet extends BasicServlet{

    @Override
    protected void doPreRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer= resp.getWriter();
        writer.print("it is doing ");
        writer.flush();
    }

    @Override
    protected void scanComponent() {
        String url="com.lmj.controller";
        ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner();
        scanner.doScan(url);
    }
}
