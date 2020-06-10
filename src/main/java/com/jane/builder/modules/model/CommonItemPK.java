package com.jane.builder.modules.model;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

@Data
public class CommonItemPK implements Serializable{

	private static final long serialVersionUID = 7928546601051474615L;

	@Id
    @Column(name = "com_no", length = C.N50)
    private String comNo;

    @Id
    @Column(name = "value", length = C.N20)
    private String value;
}
