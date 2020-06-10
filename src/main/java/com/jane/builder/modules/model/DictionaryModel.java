package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@IdClass(DictionaryPK.class)
@Table(name = "t_dictionary")
@ApiModel(description  = "字典")
public class DictionaryModel extends Model{
	
	private static final long serialVersionUID = -53313129976630583L;

	@Id
    @Column(name = "module_no", length = C.N10)
    @ApiModelProperty(value = "模块编码", name = "moduleNo", example = "0101", required = true)
	private String moduleNo;
	
	@Id
    @Column(name = "api_no", length = C.N50)
    @ApiModelProperty(value = "查询组编码", name = "apiNo", example = "addUsser", required = true)
	private String apiNo;

	@Id
    @Column(name = "field_name", length = C.N50)
    @ApiModelProperty(value = "字段名", name = "fieldName", example = "userNo", required = true)
	private String fieldName;
	
	@Id
    @Column(name = "in_out", length = C.N5)
    @ApiModelProperty(value = "输入输出", name = "inOut", example = "in", required = true)
	private String inOut;
	
	@Column(name = "field_label", length = C.N50, nullable = false)
    @ApiModelProperty(value = "字段label", name = "fieldLabel", example = "用户编码", required = true)
	private String fieldLabel;
	
	/**
	 * number(8,2)8位整数+2位小数，number(10)10位整数
	 * string(20)字符串最大长度20
	 * date(yyyy-MM-dd)日期类型并定义了格式
	 * datetime(yyyy-MM-dd hh:mm:ss)日期时间类型并定义了格式
	 */
	@Column(name = "field_type", length = C.N80, nullable = false)
    @ApiModelProperty(value = "字段类型", name = "fieldType", example = "string(20)", required = true)
	private String fieldType;
	
	/**
	 * 用于表单验证
	 */
	@Column(name = "reg_exp", length = C.N50)
    @ApiModelProperty(value = "正则", name = "regExp", example = "\\$\\{[a-zA-z0-9_]+\\}")
	private String regExp;
	
	/**
	 * 显示表格时的合计方法（COUNT.统计计数，SUM.计算合计）
	 */
	@Column(name = "total_method", length = C.N5)
    @ApiModelProperty(value = "合计行算法", name = "totalMethod", example = "SUM")
	private String totalMethod;
	
	/**
	 * 单位，用于表格合计行显示
	 */
	@Column(name = "unit", length = C.N50)
    @ApiModelProperty(value = "单位", name = "unit", example = "元")
	private String unit;
	
	/**
	 * 调用接口查询取值
	 */
	@Column(name = "query_module_no", length = C.N10)
    @ApiModelProperty(value = "查询接口模块", name = "queryModuleNo", example = "010101")
	private String queryModuleNo;
	
	/**
	 * 调用接口查询取值
	 */
	@Column(name = "query_api_no", length = C.N50)
    @ApiModelProperty(value = "查询接口", name = "queryApiNo", example = "getUsers")
	private String queryApiNo;
	
	/**
	 * 通过通用资料取值
	 */
	@Column(name = "com_no", length = C.N50)
    @ApiModelProperty(value = "通用资料", name = "comNo", example = "xxxType")
	private String comNo;
	
	/**
	 * 显示表格时是否高亮显示该字段并设置颜色
	 */
	@Column(name = "highlight_collor", length = C.N10)
    @ApiModelProperty(value = "高亮颜色", name = "highlightCollor", example = "#666888")
	private String highlightCollor;
	
	/**
	 * 显示表格时用于标记该字段是否可触发点击事件
	 */
	@Column(name = "click_event")
    @ApiModelProperty(value = "可触发点击事件", name = "clickEvent")
	private Boolean clickEvent;
	
	@Column(name = "default_value", length = C.N50)
    @ApiModelProperty(value = "默认值", name = "defaultValue", example = "admin")
	private String defaultValue;
	
	@Column(name = "example", length = C.N50)
    @ApiModelProperty(value = "例子", name = "fieldType", example = "admin")
	private String example;
	
	/**
	 * 用于表格显示
	 */
	@Column(name = "hidden", nullable = false)
    @ApiModelProperty(value = "隐藏字段", name = "hidden")
	private Boolean hidden;
	
	/**
	 * 用于表单提交验证
	 */
	@Column(name = "nullable", nullable = false)
    @ApiModelProperty(value = "可以为空", name = "nullable")
	private Boolean nullable;
}
