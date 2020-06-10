package com.jane.builder.modules.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.constant.ApiType;
import com.jane.builder.common.constant.DicConsts;
import com.jane.builder.common.standard.R;
import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.service.ApiService;
import com.jane.builder.modules.service.DictionaryService;
import com.jane.builder.modules.service.core.StdQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"core"})
@Api(tags = {"业务核心接口"})
public class CoreController {

	@Autowired
	private StdQuery stdQuery;
	
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@PostMapping(ApiType.STD)
    @ApiOperation("标准查询")
	public R executeTest(
			@RequestBody Map<String, List<Map<String, Object>>> params,
			@ApiParam(value = "模块编码", required = true) @RequestParam String moduleNo,
			@ApiParam(value = "api编码", required = true) @RequestParam String apiNo,
			@ApiParam(value = "是否取合计行", required = false, example="true") @RequestParam Boolean sum,
			@ApiParam(value = "页码", required = false, example="1") @RequestParam Integer currentPage,
			@ApiParam(value = "每页大小", required = false, example="10") @RequestParam Integer pageSize
			) {
		List<StepModel> steps = apiService.getSteps(moduleNo, apiNo);
		StepModel stepModel = steps.get(0);
		List<DictionaryModel> dictionarys = dictionaryService.getDictionarys(moduleNo, apiNo, DicConsts.OUT);
		try {
			Map<String, Object> res = stdQuery.query(stepModel.getScript(), sum, currentPage, pageSize, params, dictionarys);
			return R.ok().setData(res);
		} catch (SQLException e) {
			e.printStackTrace();
			return R.err(e.getMessage());
		}
	}
}
