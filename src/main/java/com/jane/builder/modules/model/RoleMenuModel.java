package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.jane.builder.common.standard.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_role_menu")
@IdClass(RoleMenuModel.class)
public class RoleMenuModel extends Model{

	private static final long serialVersionUID = -943485439491959277L;

	@Id
    @Column(name = "role_id")
	private Integer roleId;
	
	@Id
    @Column(name = "module_no")
	private String moduleNo;
}
