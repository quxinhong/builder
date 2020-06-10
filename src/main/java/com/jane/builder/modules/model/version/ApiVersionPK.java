package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class ApiVersionPK implements Serializable{

	private static final long serialVersionUID = -7503689222968332258L;
	
	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
	@Column(name = "api_no", length = C.N50)
	private String apiNo;

	@Id
	@Column(name = "module_no", length = C.N10)
	private String moduleNo;

}
