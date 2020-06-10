package com.jane.builder.common.exception;

import com.jane.builder.common.standard.R;

public class RException extends RuntimeException{

	private static final long serialVersionUID = 1079754839603060772L;
	
	private R r = R.err().setMsg("operation failed");
    
    public RException(String msg) {
		super(msg);
		r.setMsg(msg);
	}
    
    public RException(String msg, Integer code) {
		super(msg);
		r.setMsg(msg).setCode(code);
	}
	
	public RException(String msg, Throwable e) {
		super(msg, e);
		r.setMsg(msg);
	}
	
	public RException(Throwable e) {
		super(e);
		r.setMsg(e.getMessage());
	}

	public R getR() {
		return r;
	}
}
