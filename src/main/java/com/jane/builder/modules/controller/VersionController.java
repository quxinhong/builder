package com.jane.builder.modules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.model.VersionModel;
import com.jane.builder.modules.service.VersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"version"})
@Api(tags = {"版本管理"})
public class VersionController extends BaseController {

	@Autowired
	private VersionService versionService;
	
	@PostMapping("addVersion")
	@ApiOperation("新建版本")
	public R addVersion(@RequestBody VersionModel versionModel) {
		if(X.isBlank(versionModel.getVersionNo())) {
			return R.err("版本编码不能为空");
		}
		Operator operator = getOperator();
		versionModel = versionService.exportVersion(versionModel, operator);
		return R.ok().setData(versionModel);
	}
	
	@PostMapping("modifyVersion")
	@ApiOperation("修改版本")
	public R modifyVersion(@RequestBody VersionModel versionModel) {
		if(X.isBlank(versionModel.getVersionId())) {
			return R.err("版本id不能为空");
		}
		Operator operator = getOperator();
		versionModel = versionService.modifyVersionModel(versionModel, operator);
		return R.ok().setData(versionModel);
	}
	
	@GetMapping("getVersions")
	@ApiOperation("查询版本列表")
	public R getVersions() {
		return R.ok().setData(versionService.getVersions());
	}
	
	@GetMapping("getVersion")
	@ApiOperation("查询版本详情")
	public R getVersion(@ApiParam(value = "版本id", required = true) Integer versionId) {
		VersionModel res = versionService.getVersion(versionId);
		if(res!=null) {
			return R.ok().setData(res);
		}else {
			return R.err("查无结果");
		}
	}
}
