package com.jane.builder.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jane.builder.common.MyThreadLocal;
import com.jane.builder.common.standard.CurrentUser;
import com.jane.builder.config.OperatorHandler;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private OperatorHandler operatorHandler;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		CurrentUser user = operatorHandler.getCurrentUser();
		if(user!=null) {
			MyThreadLocal.set(user);
			return super.preHandle(req, resp, handler);
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.setStatus(401);
		resp.getWriter().write("未授权");
		return false;
	}
}
