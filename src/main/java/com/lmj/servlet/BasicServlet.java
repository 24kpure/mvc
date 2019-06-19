package com.lmj.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lmj
 * @date 2019/6/17 21:59
 * 1.负责初始化
 * 2.转接请求
 */
public abstract class BasicServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        //之后做扫描相关
        System.out.println("init");
    }


    /**
     * 统一转接请求 给子类实现
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPreRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPreRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPreRequest(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPreRequest(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPreRequest(req,resp);
    }
}
