package com.jane.builder.modules.model;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_common_item")
@IdClass(CommonItemPK.class)
@ApiModel(description  = "通用参数明细")
@AllArgsConstructor
@NoArgsConstructor
public class CommonItemModel extends Model {

	private static final long serialVersionUID = 1961872132562916258L;

	@Id
    @Column(name = "com_no", length = C.N50)
    @ApiModelProperty(hidden = true)
    private String comNo;

    @Id
    @Column(name = "value", length = C.N20)
    @ApiModelProperty(value = "通用参数值", name = "value", example = "menu", required = true)
    private String value;

    @Column(name = "label", length = C.N50, nullable = false)
    @ApiModelProperty(value = "通用参数label", name = "label", example = "菜单", required = true)
    private String label;
    
    @Column(name = "idx", columnDefinition = "tinyint")
    @ApiModelProperty(value = "序号", name = "idx", example = "1", required = true)
    private Integer idx;
    
    @Column(name = "color", length = C.N10)
    @ApiModelProperty(value = "高亮color", name = "color", example = "#888888")
    private String color;
    
    @Column(name = "bind")
    @ApiModelProperty(value = "关联信息", name = "bind")
    private String bind;
    
    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

}
