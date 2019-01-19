package org.github.helixcs.argtree.servlet;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date 2018/3/2.
 */
public class ArgTreeReflectServlet extends HttpServlet {

    public void init() {
        iniAuth();
    }

    private void iniAuth() {
        String userName = getInitParameter("username");
        String password = getInitParameter("password");
        System.out.println("username=" + userName + " , " + "password=" + password);
        return;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("clazz");
        if (StringUtils.isBlank(name) || name.indexOf(".") < 1) {
            resp.setStatus(500);
            resp.getWriter().append("error : clazz is not full class path");
        } else {
            ArgTreeReflectGenerator catReflectGenerator = ArgTreeReflectGenerator.getInstance();
            try {
                Class clazz = Class.forName(name);
                resp.getWriter().append(JSONObject.toJSONString(catReflectGenerator.generatorToNode(clazz)));
            } catch (ClassNotFoundException e) {
                try {
                    ClassLoader.getSystemClassLoader().loadClass(name);
                    resp.getWriter().append(JSONObject.toJSONString(catReflectGenerator.generatorToNode(Class.forName(name))));
                } catch (ClassNotFoundException e1) {
                    resp.setStatus(500);
                    resp.getWriter().append(e.getMessage());
                }
            }
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");//*允许任何域
        // 允许的外域请求方式
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET");
        // 在999999秒内，不需要再发送预检验请求，可以缓存该结果
        resp.setHeader("Access-Control-Max-Age", "999999");
        // 允许跨域请求包含某请求头,x-requested-with请求头为异步请求
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        resp.setContentType("application/json");
        resp.getWriter().write("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
