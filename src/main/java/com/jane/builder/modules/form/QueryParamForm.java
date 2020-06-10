package com.jane.builder.modules.form;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("查询调用表单")
public class QueryParamForm {

	private String moduleNo;
	
	private String apiNo;
	
	private Map<String, Object> param;
}
