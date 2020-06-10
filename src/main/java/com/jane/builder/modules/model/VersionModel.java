package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_version", indexes = {@Index(name = "version_no",  columnList="version_no", unique = true)})
@ApiModel(description  = "版本")
public class VersionModel extends CUModel{
	private static final long serialVersionUID = -3417546371071499166L;

	@Id
    @Column(name = "version_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "版本id", name = "versionId", example = "1")
    private Integer versionId;
	
    @Column(name = "ddl_id", nullable = false)
    @ApiModelProperty(value = "ddlId", name = "ddlId", example = "1", required = true)
    private Integer ddlId;

    @Column(name = "version_no", length = C.N50, nullable = false)
    @ApiModelProperty(value = "版本编码", name = "versionNo", example = "1.0.1", required = true)
    private String versionNo;

    @Column(name = "remark", columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(value = "备注", name = "remark", example = "版本说明")
    private String remark;

    @Column(name = "usable", nullable = false)
    @ApiModelProperty(value = "如果版本存在重大bug可置为无效", name = "usable", required = true)
    private Boolean usable;
    
}
