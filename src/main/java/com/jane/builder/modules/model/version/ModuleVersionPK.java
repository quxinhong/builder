package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class ModuleVersionPK implements Serializable {

	private static final long serialVersionUID = -2438881448824240904L;

	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
    @Column(name = "module_no", length = C.N10)
    private String moduleNo;
}
