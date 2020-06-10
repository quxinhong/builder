package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_role")
public class RoleModel extends CUModel {

	private static final long serialVersionUID = -8469411786343358627L;

	@Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_name", length = C.N20, nullable = false)
    private String roleName;

    @Column(name = "remark")
    private String remark;

}
