package com.jane.builder.common.standard;

import com.alibaba.fastjson.JSON;
import com.jane.builder.common.constant.C;
import com.jane.builder.common.enums.Err;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

public class R extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -2042446360289770104L;

	private R() {};
	
	public static R create() {
		return new R();
	}

	public static R create(Err err) {
		return new R().setCode(err.getCode()).setMsg(err.getMsg());
	}
	
	public static R ok() {
		return R.create(Err.SUCCESS);
	}
	
	public static R err(Integer code) {
		return R.create().setCode(code).setMsg("err");
	}
	
	public static R err() {
		return R.create(Err.FAIL);
	}
	
	public static R err(String msg) {
		return R.create(Err.FAIL).setMsg(msg);
	}
	
	public static R err(Integer code, String msg) {
		return R.create().setCode(code).setMsg(msg);
	}
	
	public R setCode(Integer code) {
		this.set("code", code);
		return this;
	}
	
	public R setMsg(String msg) {
		return this.set("msg", msg);
	}
	
	public R setData(Object data) {
		return set("data", data);
	}
	
	public R set(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public void write(HttpServletResponse response) {
		response.setContentType(C.CONTENT_TYPE);
		Writer w = null;
		try{
			w = response.getWriter();
			w.write(JSON.toJSONString(this));
			w.flush();
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
