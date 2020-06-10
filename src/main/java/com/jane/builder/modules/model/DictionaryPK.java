package com.jane.builder.modules.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class DictionaryPK implements Serializable{
	private static final long serialVersionUID = -6312468247616091259L;
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
}
