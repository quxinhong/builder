package com.jane.builder.modules.model;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@IdClass(ApiPK.class)
@Table(name = "t_api")
@io.swagger.annotations.ApiModel(description  = "Api模型")
public class ApiModel extends CUModel {
    private static final long serialVersionUID = -2352354475747139507L;

    @Id
    @Column(name = "api_no", length = C.N50)
    @ApiModelProperty(value = "api编码", name = "apiNo", example = "addUsser", required = true)
    private String apiNo;

    @Id
    @Column(name = "module_no", length = C.N10)
    @ApiModelProperty(value = "模块编码", name = "moduleNo", example = "0101", required = true)
    private String moduleNo;

    @Column(name = "api_name", length = C.N50, nullable = false)
    @ApiModelProperty(value = "api名称", name = "apiName", example = "新增用户", required = true)
    private String apiName;
    
    @Column(name = "api_type", nullable = false, length = C.N5)
    @ApiModelProperty(value = "接口类型（std标准查询,obj对象查询,biz业务逻辑）", name = "apiType", required = true)
    private String apiType;
    
    @Column(name = "home", nullable = false)
    @ApiModelProperty(value = "是否为首页查询（每个模块只能有一个）", name = "home", required = true)
    private Boolean home;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark", example = "备注")
    private String remark;
    
    @Column(name = "in_json", columnDefinition = "text")
    @ApiModelProperty(value = "输入", name = "inJson")
    private String inJson;
    
    @Column(name = "out_json", columnDefinition = "text")
    @ApiModelProperty(value = "输出", name = "outJson")
    private String outJson;
}
