package com.jane.builder.modules.form;

import java.util.List;

import com.jane.builder.modules.model.ApiModel;
import com.jane.builder.modules.model.DictionaryModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@io.swagger.annotations.ApiModel("数据字典表单")
public class DictionaryForm {

	private ApiModel api;
	
	private List<DictionaryModel> dictionarys;
}
