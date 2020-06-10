package com.jane.builder.modules.model.version;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class CommonItemVersionPK implements Serializable {
	
	private static final long serialVersionUID = -7147832245407148623L;
	
	@Id
	@Column(name = "version")
	private Integer version;

	@Id
    @Column(name = "com_no", length = C.N50)
    private String comNo;

    @Id
    @Column(name = "value", length = C.N20)
    private String value;
}
