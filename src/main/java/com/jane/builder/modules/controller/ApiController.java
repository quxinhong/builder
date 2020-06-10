package com.jane.builder.modules.controller;

import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.jane.builder.common.constant.ApiType;
import com.jane.builder.common.constant.DicConsts;
import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.SqlX;
import com.jane.builder.common.util.X;
import com.jane.builder.config.Env;
import com.jane.builder.modules.form.DictionaryForm;
import com.jane.builder.modules.form.QueryParamForm;
import com.jane.builder.modules.model.ApiModel;
import com.jane.builder.modules.model.ApiPK;
import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.model.StepPK;
import com.jane.builder.modules.service.ApiService;
import com.jane.builder.modules.service.DictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"api"})
@Api(tags = {"模块"})
public class ApiController extends BaseController{
	
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private Env env;

	@PostMapping("addApi")
	@ApiOperation("新建api")
	public R addApi(@RequestBody ApiModel apiModel) {
		String moduleNo = apiModel.getModuleNo();
		if(X.isBlank(moduleNo)) {
			return R.err("模块编码不能为空");
		}
		String apiNo = apiModel.getApiNo();
		if(X.isBlank(apiNo)) {
			return R.err("api编码不能为空");
		}
		if(X.isBlank(apiModel.getApiName())) {
			return R.err("api名称不能为空");
		}
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		if(apiService.existsApiById(apiPK)) {
			return R.err("主键重复");
		}
		String apiType = apiModel.getApiType();
		Boolean home = apiModel.getHome();
		if(home){
			if(!ApiType.STD.equals(apiType)){
				return R.err("首页api必须是标准查询");
			}
			ApiModel check = new ApiModel();
			check.setModuleNo(moduleNo);
			check.setHome(home);
			Example<ApiModel> example = Example.of(check);
			if(apiService.existsApiByExample(example)) {
				return R.err("此模块已存在首页查询");
			}
		}

		Operator o = getOperator();
		o.create(apiModel);
		apiService.saveApi(apiModel);
		return R.ok();
	}
	
	@PostMapping("addStep")
	@ApiOperation("新建Step")
	public R addStep(@RequestBody StepModel stepModel) {
		String moduleNo = stepModel.getModuleNo();
		if(X.isBlank(moduleNo)) {
			return R.err("模块编码不能为空");
		}
		String apiNo = stepModel.getApiNo();
		if(X.isBlank(apiNo)) {
			return R.err("api编码不能为空");
		}
		
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		ApiModel apiModel = apiService.getApiById(apiPK);
		boolean std = ApiType.STD.equals(apiModel.getApiType());
		if(std) {
			String script = stepModel.getScript();
			List<SQLStatement> statements = SQLUtils.toStatementList(script, env.getDbType());
			if(statements.size()!=1) {
				return R.err("标准查询只能包含一条查询语句");
			}
		}
		
		StepModel countStep = new StepModel();
		countStep.setModuleNo(moduleNo);
		countStep.setApiNo(apiNo);
		Example<StepModel> example = Example.of(countStep);
		Integer idx = apiService.getStepCountByExample(example);
		if(std&&idx>0) {
			return R.err("标准查询只能包含一条查询语句");
		}
		
		if(stepModel.getBatch()&&std) {
			return R.err("标准查询不能标记为批量处理");
		}
		
		String stepNo = "step"+idx;
		stepModel.setIdx(idx);
		stepModel.setStepNo(stepNo);
		Operator o = getOperator();
		o.create(stepModel);
		apiService.saveStep(stepModel);
		return R.ok().setData(stepNo);
	}
	
	@PostMapping("modifyApi")
	@ApiOperation("修改api")
	public R modifyApi(@RequestBody ApiModel apiModel) {
		String moduleNo = apiModel.getModuleNo();
		if(X.isBlank(moduleNo)) {
			return R.err("模块编码不能为空");
		}
		String apiNo = apiModel.getApiNo();
		if(X.isBlank(apiNo)) {
			return R.err("api编码不能为空");
		}
		String apiName = apiModel.getApiName();
		if(X.isBlank(apiName)) {
			return R.err("api名称不能为空");
		}
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		Optional<ApiModel> opt = apiService.findApiById(apiPK);
		if(!opt.isPresent()) {
			return R.err("记录不存在");
		}
		boolean std = ApiType.STD.equals(apiModel.getApiType());
		Boolean home = apiModel.getHome();
		if(home) {
			if(!std) {
				return R.err("首页api必须是标准查询");
			}
			ApiModel check = new ApiModel();
			check.setModuleNo(moduleNo);
			check.setHome(home);
			Example<ApiModel> example = Example.of(check);
			List<ApiModel> list = apiService.getApisByExample(example);
			if(list!=null&&list.size()>0&&!list.get(0).getApiNo().equals(apiNo)){
				return R.err("此模块已存在首页查询");
			}
		}
		
		if(std) {
			StepModel stepModel = new StepModel();
			stepModel.setApiNo(apiNo);
			stepModel.setModuleNo(moduleNo);
			Example<StepModel> example2 = Example.of(stepModel);
			Integer stepCount = apiService.getStepCountByExample(example2);
			if(stepCount>1) {
				return R.err("标准查询只能存在1条查询语句");
			}
		}
		
		ApiModel oldApi = opt.get();
		oldApi.setApiName(apiName);
		oldApi.setApiType(apiModel.getApiType());
		oldApi.setHome(home);
		oldApi.setRemark(apiModel.getRemark());
		Operator o = getOperator();
		o.update(oldApi);
		apiService.saveApi(oldApi);
		return R.ok();
	}
	
	@PostMapping("modifyStep")
	@ApiOperation("修改Step")
	public R modifyStep(@RequestBody StepModel stepModel) {
		String moduleNo = stepModel.getModuleNo();
		if(X.isBlank(moduleNo)) {
			return R.err("模块编码不能为空");
		}
		String apiNo = stepModel.getApiNo();
		if(X.isBlank(apiNo)) {
			return R.err("api编码不能为空");
		}
		Integer idx = stepModel.getIdx();
		if(X.isBlank(idx)) {
			return R.err("序号不能为空");
		}
		
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		ApiModel apiModel = apiService.getApiById(apiPK);
		boolean std = ApiType.STD.equals(apiModel.getApiType());
		if(std) {
			String script = stepModel.getScript();
			List<SQLStatement> statements = SQLUtils.toStatementList(script, env.getDbType());
			if(statements.size()!=1) {
				return R.err("标准查询只能包含一条查询语句");
			}
		}
		
		if(stepModel.getBatch()&&std) {
			return R.err("标准查询不能标记为批量处理");
		}
		
		StepPK stepPK = new StepPK();
		stepPK.setModuleNo(moduleNo);
		stepPK.setApiNo(apiNo);
		stepPK.setIdx(idx);
		Optional<StepModel> opt = apiService.findStepById(stepPK);
		if(!opt.isPresent()) {
			return R.err("记录不存在");
		}
		StepModel oldStep = opt.get();
		oldStep.setRemark(stepModel.getRemark());
		oldStep.setScript(stepModel.getScript());
		oldStep.setBatch(stepModel.getBatch());
		Operator o = getOperator();
		o.update(oldStep);
		apiService.saveStep(oldStep);
		return R.ok().setData(oldStep.getStepNo());
	}
	
	@GetMapping("delApi")
	@ApiOperation("删除api")
	public R delApi(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo
			) {
		apiService.delApi(moduleNo, apiNo);
		return R.ok();
	}
	
	@GetMapping("delStep")
	@ApiOperation("删除Step")
	public R delStep(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo,
			@ApiParam(value = "step编码", required = true) Integer idx
			) {
		Integer max = apiService.findMaxStepIdx(moduleNo, apiNo);
		if(max!=idx) { 
			return R.err("请按step序号倒序删除"); 
		}
		apiService.delStep(moduleNo, apiNo, idx);
		return R.ok();
	}
	
	@GetMapping("getStep")
	@ApiOperation("Step查询")
	public R getStep(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo,
			@ApiParam(value = "step编码", required = true) Integer idx
			) {
		StepPK stepPK = new StepPK();
		stepPK.setModuleNo(moduleNo);
		stepPK.setApiNo(apiNo);
		stepPK.setIdx(idx);
		Optional<StepModel> opt = apiService.findStepById(stepPK);
		if(opt.isPresent()) {
			return R.ok().setData(opt.get());
		}else {
			return R.err("记录不存在");
		}
	}
	
	@GetMapping("getApi")
	@ApiOperation("api查询")
	public R getApi(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo
			) {
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		Optional<ApiModel> opt = apiService.findApiById(apiPK);
		if(opt.isPresent()) {
			return R.ok().setData(opt.get());
		}else {
			return R.err("记录不存在");
		}
	}
	
	@GetMapping("getApis")
	@ApiOperation("api列表查询")
	public R getApis(@ApiParam(value = "模块编码", required = true) String moduleNo) {
		ApiModel apiModel = new ApiModel();
		apiModel.setModuleNo(moduleNo);
		Example<ApiModel> example = Example.of(apiModel);
		return R.ok().setData(apiService.getApisByExample(example));
	}
	
	@GetMapping("getSteps")
	@ApiOperation("step列表查询")
	public R getSteps(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo
			) {
		return R.ok().setData(apiService.getSteps(moduleNo, apiNo));
	}
	
	@GetMapping("generateInDictionarys")
	@ApiOperation("生成入参")
	public R generateInDictionarys(
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo
			) {
		return R.ok().setData(apiService.getDefaultDictionary(SqlX.searchParams(apiService.getSteps(moduleNo, apiNo)), moduleNo, apiNo, DicConsts.IN));
	}

	@PostMapping("generateOutDictionarys")
	@ApiOperation("生成出参")
	public R generateOutDictionarys(@RequestBody QueryParamForm queryParam) {
		Operator operator = getOperator();
		try {
			return R.ok().setData(apiService.generateOutDictionarys(queryParam, operator));
		} catch (SQLException e) {
			e.printStackTrace();
			return R.err(e.getMessage());
		}
	
	}
	
	@PostMapping("saveDictionarys")
	@ApiOperation("保存入/出参")
	public R saveDictionarys(
			@ApiParam(value = "入/出", required = true) String inOut,
			@RequestBody DictionaryForm dictionaryForm
			) {
		Operator operator = getOperator();
		apiService.saveDictionarys(inOut, dictionaryForm, operator);
		return R.ok();
	}
	
	
	@GetMapping("getDictionarys")
	@ApiOperation("字典查询")
	public R getDictionarys(
			@ApiParam(value = "入/出", required = true) String inOut,
			@ApiParam(value = "模块编码", required = true) String moduleNo,
			@ApiParam(value = "api编码", required = true) String apiNo
			) {
		return R.ok().setData(dictionaryService.getDictionarys(moduleNo, apiNo, inOut));
	}
}
