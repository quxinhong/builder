package com.jane.builder.config;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jane.builder.common.MyThreadLocal;
import com.jane.builder.common.Redis;
import com.jane.builder.common.constant.C;
import com.jane.builder.common.standard.CurrentUser;

@Component
public class OperatorHandler {

	@Autowired
	private Redis redis;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private RedisTemplate<String, CurrentUser> tokenUser;
	
	public CurrentUser getCurrentUser() {
		Cookie c = getCookie();
		CurrentUser user = null;
		if(c!=null) {
			String session = c.getValue();
			user = tokenUser.opsForValue().get(session);
		}
		return user;
	}
	
	public Cookie getCookie() {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(C.SESSION_KEY)) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	public void delCookie() {
		getCookie().setMaxAge(0);
	}
	
	public boolean setCurrentUser(CurrentUser user, String key) {
		String userNo = user.getUserNo();
		String oldToken = (String) redis.hget(userNo, C.REDIS_USER_TOKEN);
		if(oldToken!=null) {
			redis.del(oldToken);
		}
		if(redis.hset(user.getUserNo(), C.REDIS_USER_TOKEN, key)) {
			if(redis.set(key, user)) {
				return true;
			}else {
				redis.hdel(user.getUserNo(), C.REDIS_USER_TOKEN);
			}
		}
		return false;
	}
	
	public boolean login(CurrentUser user) {
		Cookie c = createUUIDCookie();
		setCookie(c);
		return setCurrentUser(user, c.getValue());
	}
	
	public void logout() {
		String userNo = MyThreadLocal.get().getUserNo();
		String token = (String) redis.hget(userNo, C.REDIS_USER_TOKEN);
		redis.hdel(userNo, C.REDIS_USER_TOKEN);
		redis.del(token);
		delCookie();
	}
	
	public void setCookie(Cookie c){
		response.addCookie(c);
	}
	
	public Cookie createUUIDCookie() {
		return createUUIDCookie("/");
	}

	public  Cookie createUUIDCookie(String path) {
		Cookie cookie = new Cookie(C.SESSION_KEY, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		cookie.setPath(path);
		cookie.setHttpOnly(true);
		return cookie;
	}
}
