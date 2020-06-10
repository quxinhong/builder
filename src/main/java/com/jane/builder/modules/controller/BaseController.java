package com.jane.builder.modules.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jane.builder.common.MyThreadLocal;
import com.jane.builder.common.standard.Operator;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected Operator getOperator() {
		return MyThreadLocal.get();
	}
}
