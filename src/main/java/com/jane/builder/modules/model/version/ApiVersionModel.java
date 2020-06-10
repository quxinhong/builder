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
@IdClass(ApiVersionPK.class)
@Table(name = "t_version_api")
public class ApiVersionModel implements Serializable {

	private static final long serialVersionUID = -4742459659143788479L;
	
	@Id
	@Column(name = "version")
	private Integer version;
	
	@Id
    @Column(name = "api_no", length = C.N50)
    private String apiNo;

    @Id
    @Column(name = "module_no", length = C.N10)
    private String moduleNo;

    @Column(name = "api_name", length = C.N50, nullable = false)
    private String apiName;
    
    @Column(name = "api_type", nullable = false, length = C.N5)
    private String apiType;
    
    @Column(name = "home", nullable = false)
    private Boolean home;

    @Column(name = "remark")
    private String remark;
    
    @Column(name = "in_json", columnDefinition = "text")
    private String inJson;
    
    @Column(name = "out_json", columnDefinition = "text")
    private String outJson;

}
