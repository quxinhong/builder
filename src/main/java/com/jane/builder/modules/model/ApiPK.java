package com.jane.builder.modules.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class ApiPK implements Serializable {
	private static final long serialVersionUID = -4203497235980850076L;
	@Id
	@Column(name = "api_no", length = C.N50)
	private String apiNo;

	@Id
	@Column(name = "module_no", length = C.N10)
	private String moduleNo;
}
