package com.jane.builder.modules.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.R;
import com.jane.builder.modules.dao.DDLRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"test"})
@Api(tags = {"测试"})
public class TestController {

	@Autowired
	private DDLRepository ddlRepository;
	
	@PostMapping("test")
    @ApiOperation("test")
	public R excute(@RequestBody Map<String, Object> map) {
		Integer maxddl = ddlRepository.findLastId();
		System.out.println(maxddl);
		for (String key : map.keySet()) {
			System.out.println(map.get(key).getClass());
		}
		return R.ok();
	}
}
