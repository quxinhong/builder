package com.jane.builder.common.util;

public class X {

	public static final String BLANK = "";
	
	public static final String SOURCE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static boolean isEmpty(Object o) {
		return o==null|| BLANK.equals(o);
	}
	
    public static boolean isBlank(Object o){
    	if(o==null) {
			return true;
		}else {
			if(o instanceof String) {
				return BLANK.equals(((String) o).trim());
			}else {
				return false;
			}
		}
    }
    
    public static boolean isInt(Class<?> clazz) {
    	return clazz==int.class||clazz==Integer.class;
    }
    
    public static boolean isLong(Class<?> clazz) {
    	return clazz==long.class||clazz==Long.class;
    }
    
    public static boolean isBoolean(Class<?> clazz) {
    	return clazz==boolean.class||clazz==Boolean.class;
    }
    
    public static String randomStr(int len) {
    	StringBuffer bf = new StringBuffer(); 
    	int sl = SOURCE.length();
    	for(int i=0; i<len; i++) {
    		Double d = Math.random()*sl;
    		bf.append(SOURCE.charAt(d.intValue()));
    	}
    	return bf.toString();
    }
    
    public static void main(String[] args) {
    	String password = "111111";
    	String salt = "mPVukBA8";
    	System.out.println(salt);
    	String enPwd = Common.encodePassword(password, salt);
    	System.out.println(enPwd);
	}
}
