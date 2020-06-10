package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
@Entity
@IdClass(DictionaryVersionPK.class)
@Table(name = "t_version_dictionary")
public class DictionaryVersionModel implements Serializable {

	private static final long serialVersionUID = -2453949646199664418L;
	
	@Id
	@Column(name = "version")
	private Integer version;

	@Id
    @Column(name = "module_no", length = C.N10)
	private String moduleNo;
	
	@Id
    @Column(name = "api_no", length = C.N50)
	private String apiNo;

	@Id
    @Column(name = "field_name", length = C.N50)
	private String fieldName;
	
	@Id
    @Column(name = "in_out", length = C.N5)
	private String inOut;
	
	@Column(name = "field_label", length = C.N50, nullable = false)
	private String fieldLabel;
	
	/**
	 * number(8,2)8位整数+2位小数，number(10)10位整数
	 * string(20)字符串最大长度20
	 * date(yyyy-MM-dd)日期类型并定义了格式
	 * datetime(yyyy-MM-dd hh:mm:ss)日期时间类型并定义了格式
	 */
	@Column(name = "field_type", length = C.N80, nullable = false)
	private String fieldType;
	
	/**
	 * 用于表单验证
	 */
	@Column(name = "reg_exp", length = C.N50)
	private String regExp;
	
	/**
	 * 显示表格时的合计方法（COUNT.统计计数，SUM.计算合计）
	 */
	@Column(name = "total_method", length = C.N5)
	private String totalMethod;
	
	/**
	 * 单位，用于表格合计行显示
	 */
	@Column(name = "unit", length = C.N50)
	private String unit;
	
	/**
	 * 调用接口查询取值
	 */
	@Column(name = "query_module_no", length = C.N10)
	private String queryModuleNo;
	
	/**
	 * 调用接口查询取值
	 */
	@Column(name = "query_api_no", length = C.N50)
	private String queryApiNo;
	
	/**
	 * 通过通用资料取值
	 */
	@Column(name = "com_no", length = C.N50)
	private String comNo;
	
	/**
	 * 显示表格时是否高亮显示该字段并设置颜色
	 */
	@Column(name = "highlight_collor", length = C.N10)
	private String highlightCollor;
	
	/**
	 * 显示表格时用于标记该字段是否可触发点击事件
	 */
	@Column(name = "click_event")
	private Boolean clickEvent;
	
	@Column(name = "default_value", length = C.N50)
	private String defaultValue;
	
	@Column(name = "example", length = C.N50)
	private String example;
	
	/**
	 * 用于表格显示
	 */
	@Column(name = "hidden", nullable = false)
	private Boolean hidden;
	
	/**
	 * 用于表单提交验证
	 */
	@Column(name = "nullable", nullable = false)
	private Boolean nullable;
}
