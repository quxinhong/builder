package com.jane.builder.common.standard;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

public class CurrentUser extends LinkedHashMap<String, Object> implements Operator{

	private static final long serialVersionUID = -6470966546010514824L;
	
	public CurrentUser() { super(); }
	
	public static CurrentUser build(User user) {
		CurrentUser cu = new CurrentUser();
		return cu.setUserNo(user.getUserNo()).setUserName(user.getUserName());
	}

	public CurrentUser setUserNo(Serializable userNo) {
		this.put("userNo", userNo);
		return this;
	}
	
	public String getUserNo() {
		return (String) this.get("userNo");
	}
	
	public CurrentUser setUserName(String userName) {
		this.put("userName", userName);
		return this;
	}
	
	public String getUserName() {
		return (String) this.get("userName");
	}

	public void set(String key, Object object) {
		this.put(key, object);
	}
	
	@Override
	public void create(CUModel model) {
		model.setCreateUser(getUserNo());
		model.setCreateDate(new Date());
	}

	@Override
	public void update(CUModel model) {
		model.setUpdateUser(getUserNo());
		model.setUpdateDate(new Date());
	}
}
