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
@IdClass(ModuleVersionPK.class)
@Table(name = "t_version_module")
public class ModuleVersionModel implements Serializable{

	private static final long serialVersionUID = 5905971797072963730L;

	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
    @Column(name = "module_no", length = C.N10)
    private String moduleNo;

    @Column(name = "module_name", length = C.N50, nullable = false)
    private String moduleName;

    @Column(name = "remark")
    private String remark;
}
