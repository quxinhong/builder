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
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@IdClass(StepPK.class)
@Table(name = "t_step")
@ApiModel(description  = "Step")
public class StepModel extends CUModel {
    private static final long serialVersionUID = 1910627615212387699L;

    @Id
    @Column(name = "module_no", length = C.N10)
    @ApiModelProperty(value = "模块编码", name = "moduleNo", example = "0101", required = true)
    private String moduleNo;

    @Id
    @Column(name = "api_no", length = C.N50)
    @ApiModelProperty(value = "Api编码", name = "apiNo", example = "addUsser", required = true)
    private String apiNo;

    @Id
    @Column(name = "idx", columnDefinition = "tinyint")
    @ApiModelProperty(value = "序号", name = "index", example = "0")
    private Integer idx;

    @Column(name = "step_no", nullable = false, length = C.N50)
    @ApiModelProperty(value = "step编码", name = "stepNo", example = "step_1", required = true)
    private String stepNo;

    @Column(name = "script", nullable = false, columnDefinition = "text")
    @ApiModelProperty(value = "脚本", name = "script", example = "INSERT INTO `user` (fields...) VALUES (values...)", required = true)
    private String script;

    @Column(name = "batch", nullable = false)
    @ApiModelProperty(value = "是否批处理", name = "batch", required = true)
    private Boolean batch;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;
}
