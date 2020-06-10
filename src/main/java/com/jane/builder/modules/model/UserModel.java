package com.jane.builder.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CUModel;
import com.jane.builder.common.standard.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_user")
@ApiModel(description  = "用户表单")
public class UserModel extends CUModel implements User{

	private static final long serialVersionUID = 7201986726630768537L;

	@Id
    @Column(name = "user_no", length = C.N20)
	@ApiModelProperty(value = "用户编码（登录用户名）", name = "userNo", example = "admin", required = true)
    private String userNo;

    @Column(name = "user_name", nullable = false, length = C.N50)
    @ApiModelProperty(value = "用户名称", name = "userName", example = "管理员")
    private String userName;

    @Column(name = "password", nullable = false, length = C.N50)
    @ApiModelProperty(value = "用户名称", name = "password", example = "123456")
    private String password;

    @ApiModelProperty(hidden = true)
    @Column(name = "salt", nullable = false, length = C.N10, updatable = false)
    private String salt;

    @Column(name = "email", length = C.N50)
    @ApiModelProperty(value = "邮箱", name = "email", example = "123@456.789")
    private String email;

    @Column(name="usable", nullable = false)
    @ApiModelProperty(value = "是否可用", name = "usable", example = "true")
    private Boolean usable;

}
