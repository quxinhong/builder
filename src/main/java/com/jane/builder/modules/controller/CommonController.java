package com.jane.builder.modules.controller;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.form.CommonForm;
import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;
import com.jane.builder.modules.service.CommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"common"})
@Api(tags = {"通用参数"})
public class CommonController extends BaseController{

    @Autowired
    private CommonService commonService;

    @PostMapping("addCommon")
    @ApiOperation("通用参数新增")
    public R addCommon(@RequestBody CommonForm common){
        CommonModel cm = common.getCommon();
        if(X.isBlank(cm.getComNo())) {
        	return R.err("通用参数编码不能为空");
        }
        if(commonService.existsCommonByComNo(cm.getComNo())) {
        	return R.err("通用参数编码已存在");
        }
        if(X.isBlank(cm.getComName())) {
        	return R.err("通用参数名称不能为空");
        }
        List<CommonItemModel> items = common.getItems();
        for (CommonItemModel cim : items) {
        	if(X.isBlank(cim.getValue())) {
        		return R.err("通用参数值不能为空");
        	}
        	if(X.isBlank(cim.getLabel())) {
        		return R.err("通用参数label不能为空");
        	}
            cim.setComNo(cm.getComNo());
        }
        Operator user = getOperator();
        user.create(cm);
        commonService.addCommon(cm, items);
        return R.ok();
    }
    
    @PostMapping("modifyCommon")
    @ApiOperation("通用参数修改")
    public R modifyCommon(@RequestBody CommonForm common){
    	CommonModel cm = common.getCommon();
        if(X.isBlank(cm.getComNo())) {
        	return R.err("通用参数编码不能为空");
        }
        if(!commonService.existsCommonByComNo(cm.getComNo())) {
        	return R.err("通用参数编码不存在");
        }
        if(X.isBlank(cm.getComName())) {
        	return R.err("通用参数名称不能为空");
        }
        List<CommonItemModel> items = common.getItems();
        for (CommonItemModel cim : items) {
        	if(X.isBlank(cim.getValue())) {
        		return R.err("通用参数值不能为空");
        	}
        	if(X.isBlank(cim.getLabel())) {
        		return R.err("通用参数label不能为空");
        	}
            cim.setComNo(cm.getComNo());
        }
        Operator user = getOperator();
        user.update(cm);
        commonService.modifyCommon(cm, items);
        return R.ok();
    }

    @GetMapping("commonList")
    @ApiOperation("通用参数列表")
    @ApiImplicitParams({
		@ApiImplicitParam(paramType="query", dataType="string", name="keyword", value="关键字"),
	})
    public R commonList(String keyword){
    	return R.ok().setData(commonService.searchCommom(keyword));
    }
    
    @GetMapping("getCommon")
    @ApiOperation("通用参数详情")
    public R getCommon(@ApiParam(value = "通用资料编码", required = true) String comNo){
    	CommonModel cmomonModel = commonService.getCommonModel(comNo);
    	if(cmomonModel==null) {
    		return R.err("查无结果");
    	}
    	List<CommonItemModel> items = commonService.getCommonItems(comNo);
        return R.ok().setData(cmomonModel).set("items", items);
    }
    
    @GetMapping("getCommonItems")
    @ApiOperation("通用参数详情")
    public R getCommonItems(@ApiParam(value = "通用资料编码", required = true) String comNo){
        return R.ok().setData(commonService.getCommonItems(comNo));
    }
}
