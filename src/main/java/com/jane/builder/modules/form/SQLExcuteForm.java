package com.jane.builder.modules.form;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("运行SQL")
public class SQLExcuteForm {

	private String script;
}
