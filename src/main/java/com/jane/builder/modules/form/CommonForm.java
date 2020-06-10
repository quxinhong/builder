package com.jane.builder.modules.form;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

@Getter
@Setter
@ApiModel("user&role表单")
public class CommonForm {

    private CommonModel common;

    private List<CommonItemModel> items;
}
