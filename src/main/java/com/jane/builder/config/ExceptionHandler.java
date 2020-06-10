package com.jane.builder.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jane.builder.common.exception.RException;
import com.jane.builder.common.standard.R;

@Component
public class ExceptionHandler extends HandlerExceptionResolverComposite {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView()); 
		if(ex instanceof RException) {
			RException e = (RException) ex;
			mav.addAllObjects(e.getR());
		}else {
			mav.addAllObjects(R.err(ex.getMessage()));
		}
		logger.error("未处理异常", ex);
		return mav;
	}
}
