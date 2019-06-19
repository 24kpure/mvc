package com.lmj.servlet;




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
public class DispatcherServlet extends BasicServlet{

    @Override
    protected void doPreRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer= resp.getWriter();
        System.out.println("start");
        writer.print("it is doing ");
        writer.flush();
    }
}
