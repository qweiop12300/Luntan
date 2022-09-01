package com.example.chen.luntan.common.api;


import jdk.nashorn.internal.parser.Token;

public enum ApiErrorCode implements IErrorCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    FAILED(-1, "操作失败"),
    /**
     * 用户注册
     */
    USER_EXISTS(201,"用户存在"),


    CANCEL(202,"取消"),

    /**
     * 未登录，Token过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * 权限不足
     */
    FORBIDDEN(403, "权限不足"),
    /**
     * 参数校验错误
     */
    VALIDATE_FAILED(404, "参数检验失败"),

    TOKEN_EXPIRED(405,"token过期或被篡改");




    private final Integer code;
    private final String message;

    ApiErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
