package com.jane.builder.modules.controller;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.model.DDLModel;
import com.jane.builder.modules.service.DDLService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"ddl"})
@Api(tags = {"DDL操作"})
public class DDLController extends BaseController {

    @Autowired
    private DDLService ddlService;

    @PostMapping("addDDL")
    @ApiOperation("DDL新增")
    public R addDDL(@RequestBody DDLModel ddl){
        if(X.isBlank(ddl.getDdlName())){
            return R.err("DDL名称不能为空");
        }
        if(X.isBlank(ddl.getScript())){
            return R.err("DDL脚本不能为空");
        }
        ddl.setDdlId(null);
        Operator o = getOperator();
        o.create(ddl);
        ddl = ddlService.save(ddl);
        return R.ok().setData(ddl);
    }

    @GetMapping("getDDLIdxs")
    @ApiOperation("DDL索引列表")
    public R getDDLIdxs(){
        return R.ok().setData(ddlService.findDDLIdxs());
    }

    @GetMapping("getDDL")
    @ApiOperation("DDL详情")
    public R getDDL(@ApiParam(value = "ddlId", required = true, example="1") Integer ddlId){
        Optional<DDLModel> o = ddlService.findById(ddlId);
        if(o.isPresent()){
            return R.ok().setData(o.get());
        }else{
            return R.err("查无结果");
        }
    }
}
