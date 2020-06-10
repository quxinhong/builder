package com.jane.builder.config;

import java.util.Date;

import javax.transaction.Transactional;

import com.jane.builder.common.constant.ApiType;
import com.jane.builder.common.constant.C;
import com.jane.builder.common.constant.DBType;
import com.jane.builder.common.constant.OrderRule;
import com.jane.builder.common.constant.QueryCondition;
import com.jane.builder.common.constant.TotalMethod;
import com.jane.builder.common.util.Common;
import com.jane.builder.common.util.X;
import com.jane.builder.modules.model.UserModel;
import com.jane.builder.modules.service.CommonService;
import com.jane.builder.modules.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitConfig {

	@Autowired
	private CommonService commonService;
	
	private void initQueryCondition() {
		commonService.deleteCommonItemsByComNo(QueryCondition.COMMON.getComNo());
		commonService.addCommon(QueryCondition.COMMON, QueryCondition.items);
	}
	
	private void initDBType() {
		commonService.deleteCommonItemsByComNo(DBType.COMMON.getComNo());
		commonService.addCommon(DBType.COMMON, DBType.items);
	}
	
	private void initApiType() {
		commonService.deleteCommonItemsByComNo(ApiType.COMMON.getComNo());
		commonService.addCommon(ApiType.COMMON, ApiType.items);
	}

	private void initTotalMethod() {
		commonService.deleteCommonItemsByComNo(TotalMethod.COMMON.getComNo());
		commonService.addCommon(TotalMethod.COMMON, TotalMethod.items);
	}

	private void initOrderRule() {
		commonService.deleteCommonItemsByComNo(OrderRule.COMMON.getComNo());
		commonService.addCommon(OrderRule.COMMON, OrderRule.items);
	}

	@Transactional
	public void initCommon() {
		initQueryCondition();
		initDBType();
		initApiType();
		initTotalMethod();
		initOrderRule();
		initAdmin();
	}
	
	@Autowired
	private UserService userService;
	
	public void initAdmin() {
		String userNo = "admin";
		if(!userService.existsUserById(userNo)) {
			String salt = X.randomStr(8);
			UserModel user = new UserModel();
			user.setPassword(Common.encodePassword("123456", salt));
			user.setSalt(salt);
			user.setUsable(true);
			user.setUserName("管理员");
			user.setUserNo(userNo);
			user.setCreateDate(new Date());
			user.setCreateUser(C.SYSTEM);
			user = userService.saveUser(user);
		}
	}
}
