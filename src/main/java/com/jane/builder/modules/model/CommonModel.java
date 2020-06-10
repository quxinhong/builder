package com.jane.builder.modules.model;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_common")
@ApiModel(description  = "通用参数")
public class CommonModel extends CUModel {

	private static final long serialVersionUID = -2809388808588181439L;

	@Id
    @Column(name = "com_no", length = C.N50)
    @ApiModelProperty(value = "通用参数编码", name = "comNo", example = "menu_type", required = true)
    private String comNo;

    @Column(name = "com_name", length = C.N50, nullable = false)
    @ApiModelProperty(value = "通用参数名称", name = "comName", example = "菜单类型", required = true)
    private String comName;
    
    @Column(name = "editable", nullable = false)
    @ApiModelProperty(value = "可修改", name = "editable", example = "true", required = true)
    private Boolean editable;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark", example = "备注")
    private String remark;
}
