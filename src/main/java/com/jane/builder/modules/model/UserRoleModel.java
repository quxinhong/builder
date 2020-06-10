package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_user_role")
@IdClass(UserRoleModel.class)
public class UserRoleModel extends Model{

	private static final long serialVersionUID = -3451923968703066517L;

	@Id
    @Column(name = "user_no", length = C.N20)
	private String userNo;
	
	@Id
    @Column(name = "role_id")
	private Integer roleId;
}
