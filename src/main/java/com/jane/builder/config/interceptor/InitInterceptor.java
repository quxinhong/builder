package com.jane.builder.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InitInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String url = req.getRequestURL().toString();
        if(url.startsWith("http://localhost")||url.startsWith("http://127.0.0.1")){
            return super.preHandle(req, resp, handler);
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(401);
        resp.getWriter().write("未授权");
        return false;
    }
}
