package com.hooper.ugg.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功，无返回数据
     */
    public static <T> Result<T> success() {
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }
    /**
     * 成功
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        return success(ResponseCode.SUCCESS, data);
    }

    /**
     * 成功
     * @param codeEnum 响应枚举
     * @param data 返回数据
     */
    public static <T> Result<T> success(ResponseCode codeEnum, T data) {
        return new Result(codeEnum.getCode(), codeEnum.getMessage(), data);
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMessage());
    }

    /**
     * 失败
     * @param msg 返回消息
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResponseCode.FAIL.getCode(),msg);
    }

    /**
     * 失败
     * @param codeEnum 响应枚举
     */
    public static <T> Result<T> fail(ResponseCode codeEnum) {
        return new Result(codeEnum.getCode(), codeEnum.getMessage());
    }

    // 通用构造器：传任意code + msg + data
    public static <T> Result<T> build(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

}