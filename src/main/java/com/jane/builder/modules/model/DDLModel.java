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
@Table(name = "t_ddl")
@ApiModel(description  = "DDL")
public class DDLModel extends CUModel {
    private static final long serialVersionUID = 1434621533327227318L;

    @Id
    @Column(name = "ddl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ddlId", name = "ddlId", example = "1")
    private Integer ddlId;

    @Column(name = "ddl_name", length = C.N50, nullable = false)
    @ApiModelProperty(value = "ddl名称", name = "ddlName", example = "createUserTable", required = true)
    private String ddlName;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark", example = "创建用户表")
    private String remark;

    @Column(name = "script", columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(value = "脚本", name = "script", required = true)
    private String script;

    public DDLModel(){
        super();
    }

    public DDLModel(Integer ddlId, String ddlName, String remark){
        this.ddlId = ddlId;
        this.ddlName = ddlName;
        this.remark = remark;
    }
}
