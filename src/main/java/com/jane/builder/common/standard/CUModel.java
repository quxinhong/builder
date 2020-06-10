package com.jane.builder.common.standard;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.jane.builder.common.constant.C;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public class CUModel extends Model{

	private static final long serialVersionUID = 3143086804305328336L;
	
	@ApiModelProperty(hidden = true)
	@Column(name="create_user", nullable=false, updatable=false, length = C.N20)
    protected String createUser;
	
	@ApiModelProperty(hidden = true)
    @Column(name="create_date", nullable=false, updatable=false)
    protected Date createDate;
    
	@ApiModelProperty(hidden = true)
    @Column(name="update_user", insertable = false, length = C.N20)
    protected String updateUser;
    
	@ApiModelProperty(hidden = true)
    @Column(name="update_date", insertable = false)
    protected Date updateDate;
}
