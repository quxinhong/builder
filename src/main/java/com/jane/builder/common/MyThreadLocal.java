package com.jane.builder.common;

import com.jane.builder.common.standard.CurrentUser;

public class MyThreadLocal {

	private static final ThreadLocal<CurrentUser> THREAD_LOCAL = new ThreadLocal<>();

	public static CurrentUser get() {
		return THREAD_LOCAL.get();
	}

	public static void set(CurrentUser user) {
		THREAD_LOCAL.set(user);
	}
}
