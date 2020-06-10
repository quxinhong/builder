package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t_menu")
@ApiModel(description  = "菜单")
public class MenuModel extends CUModel{

	private static final long serialVersionUID = 8659910147600913582L;

	/**
	 * 菜单id
	 */
	@Id
    @Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "菜单id", name = "menuId", example = "1")
	private Integer menuId;
	
	/**
	 * 父id
	 */
	@Column(name = "parent_id", nullable = false)
	@ApiModelProperty(value = "父菜单id", name = "parentId", example = "1", required = true)
	private Integer parentId;
	
	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name", length = C.N50, nullable = false)
	@ApiModelProperty(value = "菜单名称", name = "menuName", example = "用户管理", required = true)
	private String menuName;
	
	/**
	 * 模块编码
	 */
	@Column(name = "moule_no", length = C.N10)
	@ApiModelProperty(value = "模块编码", name = "moduleNo", example = "010101")
	private String moduleNo;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	@ApiModelProperty(value = "备注", name = "remark")
    private String remark;

	/**
	 * 排序
	 */
    @Column(name = "order_num")
    @ApiModelProperty(value = "排序", name = "orderNum", example = "1")
    private Integer orderNum;

    /**
     * 图标
     */
    @Column(name = "icon", length = C.N50)
    @ApiModelProperty(value = "图标", name = "icon")
    private String icon;

    
}
