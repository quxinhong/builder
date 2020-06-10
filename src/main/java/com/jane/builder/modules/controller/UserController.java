package com.jane.builder.modules.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.CurrentUser;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.Common;
import com.jane.builder.common.util.X;
import com.jane.builder.config.OperatorHandler;
import com.jane.builder.modules.form.UserForm;
import com.jane.builder.modules.model.UserModel;
import com.jane.builder.modules.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"user"})
@Api(tags = {"用户"})
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private OperatorHandler oh;
	
	@PostMapping("addUser")
    @ApiOperation("用户新增")
	public R addUser(@RequestBody UserForm user) {
		UserModel um = user.getUser();
		R r = checkFullUser(um);
		if(r==null&&userService.existsUserById(um.getUserNo())) {
			return R.err("用户编码已存在");
		}
		Common.createPassword(um);
		getOperator().create(um);
		userService.saveUser(um, user.getRoles());
		return R.ok();
	}
	
	@PostMapping("removeUser")
    @ApiOperation("删除用户")
	public R removeUser(@RequestBody UserModel user) {
		if(X.isBlank(user.getUserNo())) {
			return R.err("用户编码不能为空");
		}
		userService.deleteUser(user.getUserNo());
		return R.ok();
	}
	
	@PostMapping("modifyUser")
    @ApiOperation("用户编辑")
	public R modifyUser(@RequestBody UserForm user) {
		UserModel um = user.getUser();
		R r = checkFullUser(um);
		UserModel oldUser = null;
		if(r==null) {
			Optional<UserModel> o = userService.findUserById(um.getUserNo());
			if(!o.isPresent()) {
				return R.err("用户不存在");
			}
			oldUser = o.get();
			oldUser.setEmail(um.getEmail());
			oldUser.setUserName(um.getUserName());
			getOperator().update(oldUser);
		}
		userService.saveUser(oldUser, user.getRoles());
		return r;
	}
	
	private R checkFullUser(UserModel user) {
		if(X.isBlank(user.getUserNo())) {
			return R.err("用户编码不能为空");
		}
		if(X.isBlank(user.getPassword())) {
			return R.err("密码不能为空");
		}
		if(X.isBlank(user.getUserName())) {
			return R.err("姓名不能为空");
		}
		if(X.isBlank(user.getUsable())) {
			return R.err("usable不能为空");
		}
		return null;
	}
	
	@PostMapping("login")
    @ApiOperation("登录")
	public R login(@RequestBody UserModel user) {
		String userNo = user.getUserNo();
		if(X.isBlank(userNo)) {
			return R.err("用户编码不能为空");
		}
		String password = user.getPassword();
		if(X.isBlank(password)) {
			return R.err("密码不能为空");
		}
		Optional<UserModel> o = userService.findUserById(userNo);
		if(!o.isPresent()) {
			return R.err("用户名或密码错误");
		}
		UserModel u = o.get();
		String ePwd = Common.encodePassword(user.getPassword(), u.getSalt());
		if(!ePwd.equals(u.getPassword())) {
			return R.err("用户名或密码错误");
		}
		if(oh.login(CurrentUser.build(u))) {
			return R.ok();
		}
		return R.err("登录异常");
	}
	
	@PostMapping("logout")
    @ApiOperation("登出")
	public R logout() {
		oh.logout();
		return R.ok();
	}
	
	@PostMapping("checkLogin")
    @ApiOperation("是否已登录")
	public R checkLogin() {
		return R.ok();
	}
}
