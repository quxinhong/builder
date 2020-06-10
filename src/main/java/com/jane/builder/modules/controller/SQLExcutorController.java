package com.jane.builder.modules.controller;

import com.alibaba.fastjson.JSON;
import com.jane.builder.common.standard.R;
import com.jane.builder.modules.SqlExecutor;
import com.jane.builder.modules.form.SQLExcuteForm;
import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.service.ApiService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"sql"})
@Api(tags = {"SQL"})
public class SQLExcutorController extends BaseController{
	
	@Autowired
	private SqlExecutor sqlExecutor;
	
	@Autowired
	private ApiService apiService;
	
	@PostMapping("execute")
    @ApiOperation("运行sql")
	public R execute(@RequestBody SQLExcuteForm sqlExcuteForm) {
		System.out.println(JSON.toJSONString(sqlExcuteForm));
		return R.ok().setData(sqlExecutor.execute(sqlExcuteForm.getScript()));
	}
	
	@PostMapping("executeTest")
    @ApiOperation("测试接口")
	public R executeTest(
			@RequestBody Map<String, List<Map<String, Object>>> params,
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo,
			@ApiParam(value = "是否回滚", required = true, example="true") Boolean rollback
			) {
		List<StepModel> steps = apiService.getSteps(moduleNo, apiNo);
		try {
			Map<String, List<Map<String, Object>>> res = sqlExecutor.executeSteps(steps, params, rollback);
			return R.ok().setData(res);
		} catch (SQLException e) {
			e.printStackTrace();
			return R.err(e.getMessage());
		}
	}
}
