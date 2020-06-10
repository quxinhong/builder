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
@IdClass(CommonVersionPK.class)
@Table(name = "t_version_common")
public class CommonVersionModel implements Serializable {

	private static final long serialVersionUID = 7529597524773333973L;
	
	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
    @Column(name = "com_no", length = C.N50)
    private String comNo;

    @Column(name = "com_name", length = C.N50, nullable = false)
    private String comName;
    
    @Column(name = "editable", nullable = false)
    private Boolean editable;

    @Column(name = "remark")
    private String remark;

}
