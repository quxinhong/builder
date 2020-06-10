package com.jane.builder.modules.controller;

import com.jane.builder.common.standard.R;
import com.jane.builder.modules.service.DatabaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"database"})
@Api(tags = {"数据库"})
public class DataBaseController extends BaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("getTables")
    @ApiOperation("查询所有表格")
    public R getTables() {
        try {
            List<Map<String, Object>> list = databaseService.getTables();
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getTable")
    @ApiOperation("查询表格")
    public R getTable(@ApiParam(value = "表名", required = true) String table) {
        try {
            List<Object[]> list = databaseService.readTable(table);
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getViews")
    @ApiOperation("查询所有视图")
    public R getViews() {
        try {
            List<String> list = databaseService.getViews();
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getViewDefinition")
    @ApiOperation("查询视图定义")
    public R getViewDefinition(@ApiParam(value = "视图", required = true) String view) {
        try {
            String res = databaseService.getViewDefinition(view);
            return R.ok().setData(res);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getFunctions")
    @ApiOperation("查询所有自定义函数")
    public R getFunctions() {
        try {
            List<String> list = databaseService.getFunctions();
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getProcedures")
    @ApiOperation("查询所有存储过程")
    public R getProcedures() {
        try {
            List<String> list = databaseService.getProcedures();
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getFunctionDefinition")
    @ApiOperation("查询函数定义")
    public R getFunctionDefinition(@ApiParam(value = "函数", required = true) String function) {
        try {
            String res = databaseService.getFunctionDefinition(function);
            return R.ok().setData(res);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getTriggers")
    @ApiOperation("查询所有触发器")
    public R getTriggers() {
        try {
            List<String> list = databaseService.getTriggers();
            return R.ok().setData(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }

    @GetMapping("getTriggerDefinition")
    @ApiOperation("查询触发器定义")
    public R getTriggerDefinition(@ApiParam(value = "触发器", required = true) String trigger) {
        try {
            String res = databaseService.getTriggerDefinition(trigger);
            return R.ok().setData(res);
        } catch (SQLException e) {
            e.printStackTrace();
            return R.err("查询错误");
        }
    }
}
