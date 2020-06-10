package com.jane.builder.common.util;

import com.jane.builder.modules.model.UserModel;

public class Common {

	public static String encodePassword(String password, String salt) {
		return StringUtil.toMD5HexString(StringUtil.toMD5HexString(password + salt).toLowerCase().substring(4, 24)).toLowerCase();
	}
	
	public static void createPassword(UserModel user) {
		String salt = X.randomStr(8);
		String enPwd = encodePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(enPwd);
	}
	
	public static void main(String[] args) {
		String salt = X.randomStr(8);
		System.out.println(salt);
		System.out.println(encodePassword("123456", salt));
	}
}
