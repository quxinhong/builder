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
@IdClass(StepVersionPK.class)
@Table(name = "t_version_step")
public class StepVersionModel implements Serializable {

	private static final long serialVersionUID = 5699118405730365871L;

	@Id
	@Column(name = "version")
	private Integer version;

	@Id
	@Column(name = "module_no", length = C.N10)
	private String moduleNo;

	@Id
	@Column(name = "api_no", length = C.N50)
	private String apiNo;

	@Id
	@Column(name = "idx", columnDefinition = "tinyint")
	private Integer idx;

	@Column(name = "step_no", nullable = false, length = C.N50)
	private String stepNo;

	@Column(name = "script", nullable = false, columnDefinition = "text")
	private String script;

	@Column(name = "batch", nullable = false)
	private Boolean batch;

	@Column(name = "remark")
	private String remark;
}
