package com.jane.builder.modules.form;

import java.util.Set;

import com.jane.builder.modules.model.UserModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("user&role表单")
public class UserForm {

	private UserModel user;
	
	private Set<Integer> roles;
}
