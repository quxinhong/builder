package com.jane.builder.common.enums;

public enum Err {

    SUCCESS(0, "ok", "成功"),

    FAIL(1, "err", "未知异常"),

    INVALID_PARAM(2, null, "参数无效，消息自定义"),
    
    DUPLICATE_PRIMARY(3, null, "插入键值重复，消息自定义"),

    UNLOGIN(1001, "未登录", "未登录"),

    USER_NO_CAN_NOT_BE_NULL(1002, "用户编码不能为空", "用户编码不能为空"),

    PWD_CAN_NOT_BE_NULL(1003, "密码不能为空", "密码不能为空"),

    LOGIN_USER_IS_NOT_FIND(1004, "用户名或密码错误", "登录用户不存在"),

    PWD_MISMATCH(1005, "用户名或密码错误", "密码不正确"),

    LOGIN_EXCEPTION_REDIS_SET_FAIL(1006, "登录异常", "redis保存用户失败")
    ;

    private int code;
    private String msg;
    private String desc;
    Err(int code, String msg, String desc){
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }
    public int getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
    public String getDesc(){
        return this.desc;
    }
}
