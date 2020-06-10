package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class CommonVersionPK implements Serializable {
	
	private static final long serialVersionUID = -2246619507440100706L;
	
	@Id
	@Column(name = "com_no", length = C.N50)
	private String comNo;

	@Id
	@Column(name = "version")
	private Integer version;

}
