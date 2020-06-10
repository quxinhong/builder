package com.jane.builder.modules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.R;
import com.jane.builder.config.InitConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"init"})
@Api(tags = {"初始化配置项"})
public class InitController {

	@Autowired
	private InitConfig initConfig;
	
	@GetMapping("initCommon")
    @ApiOperation("初始化通用参数")
    public R commonList(){
		initConfig.initCommon();
    	return R.ok();
    }
}
