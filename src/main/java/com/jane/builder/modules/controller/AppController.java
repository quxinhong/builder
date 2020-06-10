package com.jane.builder.modules.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.model.AppModel;
import com.jane.builder.modules.service.AppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"app"})
@Api(tags = {"应用管理"})
public class AppController extends BaseController{

	@Autowired
	private AppService appService;
	
	@PostMapping("addApp")
	@ApiOperation("新建app")
	public R addApp(@RequestBody AppModel app) {
		if(X.isBlank(app.getAppNo())) {
			return R.err("app编码不能为空");
		}
		if(X.isBlank(app.getAppName())) {
			return R.err("app名称不能为空");
		}
		if(X.isBlank(app.getAppNo())) {
			return R.err("app简称不能为空");
		}
		if(appService.existsById(app.getAppNo())) {
			return R.err("app编码已存在");
		}
		Operator o = getOperator();
		o.create(app);
		appService.save(app);
		return R.ok();
	}
	
	@PostMapping("modifyApp")
	@ApiOperation("修改app")
	public R modifyApp(@RequestBody AppModel app) {
		if(X.isBlank(app.getAppNo())) {
			return R.err("app编码不能为空");
		}
		if(X.isBlank(app.getAppName())) {
			return R.err("app名称不能为空");
		}
		if(X.isBlank(app.getAppNo())) {
			return R.err("app简称不能为空");
		}
		if(!appService.existsById(app.getAppNo())) {
			return R.err("app不存在");
		}
		Operator o = getOperator();
		o.update(app);
		appService.save(app);
		return R.ok();
	}
	
	@GetMapping("delApp")
	@ApiOperation("删除app")
	public R modifyApp(@ApiParam(value = "appNo") @RequestParam(required = true) String appNo) {
		appService.deleteById(appNo);
		return R.ok();
	}
	
	@GetMapping("getApp")
	@ApiOperation("查询app")
	public R getApp(@ApiParam(value = "appNo") @RequestParam(required = true) String appNo) {
		Optional<AppModel> o = appService.findById(appNo);
		if(o.isPresent()) {
			return R.ok().setData(o.get());
		}
		return R.err("app不存在");
	}
	
	@GetMapping("getApps")
	@ApiOperation("查询app列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query", dataType="string", name="keyword", value="关键字"),
	})
	public R getApps(String keyword) {
		return R.ok().setData(appService.search(keyword));
	}
}
