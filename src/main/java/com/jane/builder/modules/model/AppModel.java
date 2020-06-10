package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_app")
public class AppModel extends CUModel{

	private static final long serialVersionUID = -4192677644824146536L;

	@Id
    @Column(name = "app_no", length = C.N20)
	private String appNo;
	
	@Column(name = "app_name", length = C.N80, nullable = false)
	private String appName;
	
	@Column(name = "app_short_name", length = C.N20, nullable = false)
	private String appShortName;
	
	@Column(name = "remark")
	private String remark;
}
