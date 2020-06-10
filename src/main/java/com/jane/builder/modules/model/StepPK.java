package com.jane.builder.modules.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jane.builder.common.constant.C;

import lombok.Data;

@Data
public class StepPK implements Serializable{
	private static final long serialVersionUID = -259383964784122689L;
	@Id
    @Column(name = "module_no", length = C.N10)
    private String moduleNo;
	@Id
    @Column(name = "api_no", length = C.N50)
    private String apiNo;
    @Id
    @Column(name = "idx", columnDefinition = "tinyint")
    private Integer idx;
}
