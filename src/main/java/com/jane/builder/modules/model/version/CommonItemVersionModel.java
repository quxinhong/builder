package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@IdClass(CommonItemVersionPK.class)
@Table(name = "t_version_common_item")
public class CommonItemVersionModel implements Serializable {
	
	private static final long serialVersionUID = -1790568031107735967L;

	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
    @Column(name = "com_no", length = C.N50)
    @ApiModelProperty(hidden = true)
    private String comNo;

    @Id
    @Column(name = "value", length = C.N20)
    private String value;

    @Column(name = "label", length = C.N50, nullable = false)
    private String label;
    
    @Column(name = "idx", columnDefinition = "tinyint")
    private Integer idx;
    
    @Column(name = "color", length = C.N10)
    private String color;
    
    @Column(name = "bind")
    @ApiModelProperty(value = "关联信息", name = "bind")
    private String bind;
    
    @Column(name = "remark")
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;
}
