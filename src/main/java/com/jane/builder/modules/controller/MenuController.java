package com.jane.builder.modules.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.standard.R;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.model.MenuModel;
import com.jane.builder.modules.service.MenuServicce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({"menu"})
@Api(tags = {"菜单"})
public class MenuController extends BaseController{

	@Autowired
	private MenuServicce menuServicce;
	
	@PostMapping("saveMenu")
	@ApiOperation("新建菜单")
	public R addMenu(@RequestBody MenuModel menu) {
		if(X.isBlank(menu.getParentId())) {
			return R.err("父菜单不能为空");
		}
		if(X.isBlank(menu.getMenuName())) {
			return R.err("菜单名称不能为空");
		}
		Integer menuId = menu.getMenuId();
		Operator user = getOperator();
		if(menuId==null) {
			user.create(menu);
			menu = menuServicce.addMenu(menu);
			menuId = menu.getMenuId();
		}else {
			Optional<MenuModel> o = menuServicce.findMenuById(menuId);
			if(!o.isPresent()) {
				return R.err("菜单不存在");
			}
			MenuModel old = o.get();
			old.setMenuName(menu.getMenuName());
			old.setOrderNum(menu.getOrderNum());
			old.setRemark(menu.getRemark());
			old.setIcon(menu.getIcon());
			user.update(old);
			menuServicce.modifyMenu(old);
		}
		return R.ok().setData(menuId);
	}
	
	
	
	@GetMapping("getMenus")
	@ApiOperation("菜单列表查询")
	public R getMenus() {
		return R.ok().setData(menuServicce.findAllMenu());
	}
	
	@GetMapping("getMenu")
	@ApiOperation("菜单查询")
	public R getMenu(@ApiParam(value = "menuId", example="1", required = true) Integer menuId) {
		Optional<MenuModel> o = menuServicce.findMenuById(menuId);
		if(o.isPresent()) {
			return R.ok().setData(o.get());
		}else {
			return R.err("查无结果");
		}
	}
	
	@GetMapping("delMenu")
	@ApiOperation("删除菜单")
	public R delMenu(@ApiParam(value = "menuId", example="1", required = true) Integer menuId) {
		menuServicce.deleteMenu(menuId);
		return R.ok();
	}
}
