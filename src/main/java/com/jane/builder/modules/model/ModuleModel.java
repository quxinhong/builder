package com.jane.builder.modules.model;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_module")
@ApiModel(description  = "模块")
public class ModuleModel extends CUModel {

    private static final long serialVersionUID = 3041748071210543693L;

    @Id
    @Column(name = "module_no", length = C.N10)
    @ApiModelProperty(value = "模块编码", name = "moduleNo", example = "0101", required = true)
    private String moduleNo;

    @Column(name = "module_name", length = C.N50, nullable = false)
    @ApiModelProperty(value = "模块名称", name = "moduleName", example = "用户管理", required = true)
    private String moduleName;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark", example = "备注")
    private String remark;
}
